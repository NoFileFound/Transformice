package org.bytearray;

// Imports
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public final class ByteArray {
    private final List<Byte> buffer;

    /**
     * Creates a new array of bytes.
     */
    public ByteArray() {
        this(0);
    }

    /**
     * Creates a new array of bytes by given size.
     * @param size The given size.
     */
    public ByteArray(int size) {
        this.buffer = new ArrayList<>(size);
    }

    /**
     * Creates a new array of bytes by given bytes.
     * @param buffer The given byte array.
     */
    public ByteArray(byte[] buffer) {
        this.buffer = new ArrayList<>(buffer.length);
        for (byte b : buffer) {
            this.buffer.add(b);
        }
    }

    /**
     * Returns the length of bytearray.
     * @return The length.
     */
    public int getLength() {
        return this.buffer.size();
    }

    /**
     * Returns the bytes from the bytearray.
     * @return The bytes.
     */
    public synchronized byte[] toByteArray() {
        byte[] result = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            result[i] = buffer.get(i);
        }
        return result;
    }

    /**
     * Reads a byte from the bytearray.
     * @return A byte.
     */
    public synchronized byte readByte() {
        byte info = this.buffer.getFirst();
        this.buffer.removeFirst();
        return info;
    }

    /**
     * Reads a boolean from the bytearray.
     * @return A boolean.
     */
    public synchronized boolean readBoolean() {
        byte info = this.buffer.getFirst();
        this.buffer.removeFirst();
        return info != 0;
    }

    /**
     * Reads a short from the bytearray.
     * @return A short.
     */
    public short readShort() {
        byte high = this.readByte();
        byte low = this.readByte();
        return (short) ((high << 8) | (low & 0xFF));
    }

    /**
     * Reads an integer from the bytearray.
     * @return An integer.
     */
    public int readInt() {
        byte b1 = this.readByte();
        byte b2 = this.readByte();
        byte b3 = this.readByte();
        byte b4 = this.readByte();
        return ((b1 & 0xFF) << 24) | ((b2 & 0xFF) << 16) | ((b3 & 0xFF) << 8)  | (b4 & 0xFF);
    }

    /**
     * Reads a long from the bytearray.
     * @return A Long.
     */
    public long readLong() {
        byte b1 = this.readByte();
        byte b2 = this.readByte();
        byte b3 = this.readByte();
        byte b4 = this.readByte();
        return ((long) (b1 & 0xFF) << 24) | (long)((b2 & 0xFF) << 16) | (long)((b3 & 0xFF) << 8)  | (long)(b4 & 0xFF);
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
            cur = readUnsignedByte();
            result |= (cur & 0x7F) << (count * 7);
            signBits <<= 7;
            count++;
        }

        if (((signBits >> 1) & result) != 0) {
            result |= signBits;
        }

        return result;
    }

    /**
     * Reads an unsigned byte from the bytearray.
     * @return An unsigned byte.
     */
    public int readUnsignedByte() {
        return this.readByte() & 0xFF;
    }

    /**
     * Reads an unsigned short from the bytearray.
     * @return An unsigned short.
     */
    public int readUnsignedShort() {
        return (this.readUnsignedByte() << 8) | this.readUnsignedByte();
    }

    /**
     * Reads a bytes from the bytearray.
     * @param length The length.
     * @return An array of bytes.
     */
    public byte[] readBytes(int length) {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = this.readByte();
        }
        return result;
    }

    /**
     * Reads a string from the bytearray.
     * @return A string.
     */
    public String readString() {
        int length = this.readUnsignedShort();
        byte[] utfBytes = new byte[length];
        for (int i = 0; i < length; i++) {
            utfBytes[i] = this.readByte();
        }
        return new String(utfBytes, java.nio.charset.StandardCharsets.UTF_8);
    }

    /**
     * Writes a byte to the bytearray.
     */
    public ByteArray writeByte(int value) {
        this.buffer.add((byte) value);
        return this;
    }

    /**
     * Writes bytes to the bytearray.
     */
    public ByteArray writeBytes(byte[] bytes) {
        for (byte b : bytes) {
            this.writeByte(b);
        }
        return this;
    }

    /**
     * Writes a boolean to the bytearray.
     */
    public ByteArray writeBoolean(boolean value) {
        this.buffer.add((byte)(value ? 1 : 0));
        return this;
    }

    /**
     * Writes a float to the bytearray.
     */
    public ByteArray writeFloat(float value) {
        this.buffer.add((byte)(Float.floatToIntBits(value)));
        return this;
    }

    /**
     * Writes a short to the bytearray.
     */
    public ByteArray writeShort(short value) {
        this.writeByte((byte) (value >> 8));
        this.writeByte((byte) (value));
        return this;
    }

    /**
     * Writes an integer to the bytearray.
     */
    public ByteArray writeInt(int value) {
        this.writeByte((byte) (value >> 24));
        this.writeByte((byte) (value >> 16));
        this.writeByte((byte) (value >> 8));
        this.writeByte((byte) (value));
        return this;
    }

    /**
     * Writes an integer to the bytearray.
     */
    public ByteArray writeInt(long value) {
        this.writeByte((byte) (value >> 24));
        this.writeByte((byte) (value >> 16));
        this.writeByte((byte) (value >> 8));
        this.writeByte((byte) (value));
        return this;
    }

    /**
     * Writes an unsigned byte to the bytearray.
     */
    public ByteArray writeUnsignedByte(int value) {
        this.writeByte((byte)value);
        return this;
    }

    /**
     * Writes an unsigned short to the bytearray.
     */
    public ByteArray writeUnsignedShort(int value) {
        this.writeShort((short) value);
        return this;
    }

    /**
     * Writes an unsigned integer to the bytearray.
     */
    public ByteArray writeUnsignedInt(long value) {
        this.writeInt((int)value);
        return this;
    }

    /**
     * Writes a new integer to the bytearray.
     * @param arg1 The given new integer (new game structure)
     */
    public ByteArray writeInt128(int arg1) {
        int remaining = arg1 >> 7;
        boolean hasMore = true;
        int end = ((arg1 & 0x80000000) == 0) ? 0 : -1;
        while (hasMore) {
            hasMore = (remaining != end) || ((remaining & 1) != ((arg1 >> 6) & 1));
            int byteToWrite = (arg1 & 0x7F) | (hasMore ? 0x80 : 0);
            writeByte(byteToWrite);
            arg1 = remaining;
            remaining >>= 7;
        }

        return this;
    }

    /**
     * Writes a string to the bytearray.
     */
    public ByteArray writeString(String value, boolean writeLen) {
        byte[] utfBytes = value.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        if(writeLen) this.writeUnsignedShort(utfBytes.length);
        for (byte b : utfBytes) {
            this.writeByte(b);
        }

        return this;
    }

    /**
     * Writes a string to the bytearray.
     */
    public ByteArray writeString(String value) {
        return this.writeString(value, true);
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

        for (int i = 0; i < 20; i++) {
            _hash = (_hash << 5) + _hash + (keys.get(i) + sBytes[i % sLen]);
        }

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
     * Decrypts the protected packets.
     * @param keys The game packet keys.
     * @param key The decryption authorization key.
     */
    public ByteArray decryptIdentification(List<Integer> keys, String key) {
        ByteArray decoded = new ByteArray();
        List<Long> chunks = new ArrayList<>();
        List<Integer> computedKeys = this.computeKeys(keys, key);

        short chunk_size = this.readShort();
        for (int i = 0; i < chunk_size; i++) {
            chunks.add(this.readLong());
        }

        List<Long> decodedChunks = this.decodeChunks(chunks, computedKeys);
        for (Long chunk : decodedChunks) {
            decoded.writeInt(chunk);
        }

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
        for (int i = 0; i < this.buffer.size(); i++) {
            this.buffer.set(i, (byte) ((this.buffer.get(i) ^ keys.get((packetId + i) % 20)) & 0xFF));
        }
    }

    /**
     * Converts the bytearray to string.
     */
    @Override
    public String toString() {
        if (this.buffer == null || this.buffer.isEmpty()) {
            return "";
        }

        StringBuilder hexString = new StringBuilder();
        for (byte b : this.buffer) {
            hexString.append(String.format("%02X", b)).append(" ");
        }

        return hexString.toString().trim();
    }
}