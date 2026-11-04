package org.transformice.network;

// Imports
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.psframework.utils.PSByteArray;

public final class NetByteArray extends PSByteArray {
    /**
     * Creates a new empty bytearray.
     */
    public NetByteArray() {
        super();
    }

    /**
     * Creates a new bytearray.
     * @param packetData The given bytes.
     */
    public NetByteArray(byte[] packetData) {
        super(packetData);
    }

    /**
     * Reads a INT128 from the bytearray.
     * @return An INT128.
     */
    public int readInt128() {
        int result = 0;
        int cur = 0x80;
        int count = 0;
        int signBits = -1;
        while ((cur & 0x80) == 0x80 && count < 5) {
            cur = this.readUnsignedByte();
            result |= (cur & 0x7F) << (count * 7);
            signBits <<= 7;
            count++;
        }

        if (((signBits >> 1) & result) != 0)
            result |= signBits;

        return result;
    }

    /**
     * Reads a string from the bytearray.
     * @return A string.
     */
    public String readUTF() {
        int length = this.readUnsignedShort();
        byte[] utfBytes = new byte[length];
        for (int i = 0; i < length; i++)
            utfBytes[i] = this.readByte();

        return new String(utfBytes, StandardCharsets.UTF_8);
    }

    /**
     * Writes a new integer to the bytearray.
     * @param arg1 The given new integer (new game structure)
     */
    public NetByteArray writeInt128(int arg1) {
        int remaining = arg1 >> 7;
        boolean hasMore = true;
        int end = ((arg1 & 0x80000000) == 0) ? 0 : -1;
        while (hasMore) {
            hasMore = (remaining != end) || ((remaining & 1) != ((arg1 >> 6) & 1));
            this.writeByte((arg1 & 0x7F) | (hasMore ? 0x80 : 0));
            arg1 = remaining;
            remaining >>= 7;
        }

        return this;
    }

    /**
     * Writes a string to the bytearray.
     */
    public NetByteArray writeUTF(String value, boolean writeLen) {
        byte[] utfBytes = value.getBytes(StandardCharsets.UTF_8);
        if (writeLen)
            this.writeUnsignedShort(utfBytes.length);

        for (byte b : utfBytes)
            this.writeByte(b);

        return this;
    }

    /**
     * Creates keys to decrypt the packet.
     * @param keys The given packet keys.
     * @param s The given key.
     * @return Keys to decrypt the packet.
     */
    private List<Integer> computeKeys(List<Integer> keys, String s) {
        int sLen = s.length();
        List<Integer> buf = new ArrayList<>();
        int _hash = 5381;
        byte[] sBytes = s.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < 20; i++)
            _hash = (_hash << 5) + _hash + (keys.get(i) + sBytes[i % sLen]);

        for (int i = 0; i < 20; i++) {
            _hash ^= (_hash << 13);
            _hash ^= (_hash >> 17);
            _hash ^= (_hash << 5);
            buf.add(_hash);
        }

        return buf;
    }

    /**
     * Decodes the chunks of current packet.
     * @param chunks The given chunks.
     * @param keys The given keys.
     * @return Decoded chunks.
     */
    public List<Long> decodeChunks(List<Long> chunks, List<Integer> keys) {
        int n = chunks.size();
        final long DELTA = 0x9E3779B9L;
        int rounds = 6 + 52 / n;
        long sum = rounds * DELTA;
        long y = chunks.getFirst();
        for (int i = 0; i < rounds; i++) {
            int e = (int) ((sum >> 2) & 3);
            for (int p = n - 1; p >= 0; p--) {
                long w = chunks.get(p);
                long z = chunks.get((p - 1 + n) % n); // Circular indexing
                y = (w - (((z >>> 5) ^ (y << 2)) + ((y >>> 3) ^ (z << 4)) ^ ((sum ^ y) + (keys.get((p & 3) ^ e) ^ z)))) & 0xFFFFFFFFL;
                chunks.set(p, y);
            }
            sum = (sum - DELTA) & 0xFFFFFFFFL;
        }
        return chunks;
    }

    /**
     * Encodes the chunks of current packet.
     * @param chunks The given chunks.
     * @param keys The given keys.
     * @return Encoded chunks.
     */
    public List<Long> encodeChunks(List<Long> chunks, List<Integer> keys) {
        int n = chunks.size();
        if (n == 0)
            return chunks;

        final long DELTA = 0x9E3779B9L;
        int rounds = 6 + 52 / n;
        long sum = 0;
        long z = chunks.get(n - 1);
        for (int i = 0; i < rounds; i++) {
            sum = (sum + DELTA) & 0xFFFFFFFFL;
            int e = (int) ((sum >> 2) & 3);
            for (int p = 0; p < n; p++) {
                long y = chunks.get((p + 1) % n);
                long w = chunks.get(p);
                z = (w + (((z >>> 5) ^ (y << 2)) + ((y >>> 3) ^ (z << 4)) ^ ((sum ^ y) + (keys.get((p & 3) ^ e) ^ z)))) & 0xFFFFFFFFL;
                chunks.set(p, z);
            }
        }

        return chunks;
    }

    /**
     * Encrypts the protected packets.
     * @param keys THe game packet keys.
     * @param key The encryption authorization key.
     */
    public NetByteArray encryptIdentification(List<Integer> keys, String key) {
        NetByteArray encoded = new NetByteArray();
        List<Long> chunks = new ArrayList<>();
        List<Integer> computedKeys = this.computeKeys(keys, key);
        while (this.getLength() >= 4)
            chunks.add((long) this.readInt());

        List<Long> encodedChunks = this.encodeChunks(chunks, computedKeys);
        encoded.writeShort((short) encodedChunks.size());
        for (Long chunk : encodedChunks)
            encoded.writeInt(chunk);

        return encoded;
    }

    /**
     * Decrypts the protected packets.
     * @param keys The game packet keys.
     * @param key The decryption authorization key.
     */
    public NetByteArray decryptIdentification(List<Integer> keys, String key) {
        NetByteArray decoded = new NetByteArray();
        List<Long> chunks = new ArrayList<>();
        List<Integer> computedKeys = this.computeKeys(keys, key);
        short chunk_size = this.readShort();
        for (int i = 0; i < chunk_size; i++)
            chunks.add(this.readLong());

        List<Long> decodedChunks = this.decodeChunks(chunks, computedKeys);
        for (Long chunk : decodedChunks)
            decoded.writeInt(chunk);

        return decoded;
    }

    /**
     * Decrypts the protected packets.
     * @param keys The game packet keys.
     * @param packetId The packet id.
     */
    public void decryptPacket(List<Integer> keys, int packetId) {
        keys = this.computeKeys(keys, "msg");
        packetId++;
        List<Byte> buffer = this.getBuffer();
        for (int i = 0; i < buffer.size(); i++)
            buffer.set(i, (byte) ((buffer.get(i) ^ keys.get((packetId + i) % 20)) & 0xFF));
    }
}