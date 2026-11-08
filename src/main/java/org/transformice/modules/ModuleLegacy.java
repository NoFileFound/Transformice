package org.transformice.modules;

// Imports
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.psframework.utils.PSTimer;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.data.objects.RoomType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.MapEntry;
import org.transformice.modules.impl.ModuleServer;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.informations.PacketSendTotemItemCount;

public final class ModuleLegacy extends ModuleServer {
    /**
     * Creates a new packet legacy handler module.
     *
     * @param server The game's server.
     */
    public ModuleLegacy(GameServer server) {
        super(server);
    }

    /**
     * Handles the legacy packet.
     * @param player The player's instance.
     * @param psData The packet's bytearray.
     */
    public void handleLegacyPacket(GamePlayer player, NetByteArray psData) {
        psData.readShort(); // packet length
        int packetId = (psData.readUnsignedByte() << 8) | (psData.readUnsignedByte() & 0xFF);
        if (psData.getLength() > 0)
            psData.readByte(); // 01

        switch (packetId) {
            case LegacyPacketOpcodes.RecvDrawingClear:
                this.handleRecvDrawingClear(player, psData);
                break;
            case LegacyPacketOpcodes.RecvDrawingPoint:
                this.handleRecvDrawingPoint(player, psData);
                break;
            case LegacyPacketOpcodes.RecvDrawing:
                this.handleRecvDrawing(player, psData);
                break;
            case LegacyPacketOpcodes.RecvMapAddConjuration:
                this.handleRecvMapAddConjuration(player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvMapStartConjuration:
                this.handleRecvMapStartConjuration(player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvMapEndConjuration:
                this.handleRecvMapEndConjuration(player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvRoomAnchors:
                this.handleRecvRoomAnchors(player, player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvRoomBombExplode:
                this.handleRecvRoomBombExplode(player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvRoomBombState:
                this.handleRecvRoomBombState(player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvTotemAnchors:
                this.handleRecvTotemAnchors(player, player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvRoomMoveCheese:
                this.handleRecvRoomMoveCheese(player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvMapVote:
                this.handleRecvMapVote(player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvMapEditorLoadMap:
                this.handleRecvMapEditorLoadMap(player, player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvMapEditorValidateMap:
                this.handleRecvMapEditorValidateMap(player, player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvMapEditorXml:
                this.handleRecvMapEditorMapXml(player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvMapEditorReturnBack:
                this.handleRecvMapEditorReturnBack(player, player.getPlayerRoom());
                break;
            case LegacyPacketOpcodes.RecvMapEditorExportMap:
                this.handleRecvMapEditorExportMap(player, player.getPlayerRoom(), psData);
                break;
            case LegacyPacketOpcodes.RecvMapEditorResetMap:
                this.handleRecvMapEditorResetMap(player.getPlayerRoom());
                break;
            case LegacyPacketOpcodes.RecvMapEditorExit:
                this.handleRecvMapEditorExit(player);
                break;
            default:
                this.server.getLogger().debug(String.format("[PacketHandler] [%s] Unhandled legacy packet: %s", player.getIpAddress(), packetId));
                break;
        }
    }

    /**
     * Handles the RecvDrawing packet.
     * @param player The player's instance.
     * @param psData The packet's data.
     */
    private void handleRecvDrawing(GamePlayer player, NetByteArray psData) {
        if (!player.hasStaffPermission(StaffRoleType.ADMIN, false))
            return;

        player.getPlayerRoom().broadcastLegacyPacket(player, new PSSendPacket(LegacyPacketOpcodes.SendDrawing) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        });
    }

    /**
     * Handles the RecvDrawingClear packet.
     * @param player The player's instance.
     * @param psData The packet's data.
     */
    private void handleRecvDrawingClear(GamePlayer player, NetByteArray psData) {
        if (!player.hasStaffPermission(StaffRoleType.ADMIN, false))
            return;

        player.getPlayerRoom().broadcastLegacyPacket(player, new PSSendPacket(LegacyPacketOpcodes.SendDrawingClear) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        });
    }

    /**
     * Handles the RecvDrawingPoint packet.
     * @param player The player's instance.
     * @param psData The packet's data.
     */
    private void handleRecvDrawingPoint(GamePlayer player, NetByteArray psData) {
        if (!player.hasStaffPermission(StaffRoleType.ADMIN, false))
            return;

        player.getPlayerRoom().broadcastLegacyPacket(player, new PSSendPacket(LegacyPacketOpcodes.SendDrawingPoint) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        });
    }

    /**
     * Handles the RecvMapAddConjuration packet.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvMapAddConjuration(GameRoom playerRoom, NetByteArray psData) {
        if (!playerRoom.getCurrentMap().isConj)
            return;


        new PSTimer(this.server.getScheduler()).schedule(() -> playerRoom.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapDestroyConjuration) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        }), 4, TimeUnit.SECONDS);

        playerRoom.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapAddConjuration) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        });
    }

    /**
     * Handles the RecvMapStartConjuration packet.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvMapStartConjuration(GameRoom playerRoom, NetByteArray psData) {
        if (!playerRoom.getCurrentMap().isConj)
            return;

        playerRoom.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapStartConjuration) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        });
    }

    /**
     * Handles the RecvMapEndConjuration packet.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvMapEndConjuration(GameRoom playerRoom, NetByteArray psData) {
        if (!playerRoom.getCurrentMap().isConj)
            return;

        playerRoom.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEndConjuration) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        });
    }

    /**
     * Handles the RecvRoomAddAnchor packet.
     * @param player The player's instance.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvRoomAnchors(GamePlayer player, GameRoom playerRoom, NetByteArray psData) {
        if (!player.isShaman)
            return;

        String[] values = new String(psData.toByteArray(), StandardCharsets.UTF_8).split(String.valueOf('\u0001'));
        playerRoom.sendAddAnchor(Arrays.asList(values));
    }

    /**
     * Handles the RecvRoomBombExplode packet.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvRoomBombExplode(GameRoom playerRoom, NetByteArray psData) {
        playerRoom.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendRoomBombExplode) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        });
    }

    /**
     * Handles the RecvRoomBombState packet.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvRoomBombState(GameRoom playerRoom, NetByteArray psData) {
        playerRoom.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendRoomBombState) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        });
    }

    /**
     * Handles the RecvRoomMoveCheese packet.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvRoomMoveCheese(GameRoom playerRoom, NetByteArray psData) {
        if (!playerRoom.getCurrentMap().isCatchTheCheese)
            return;

        playerRoom.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendRoomMoveCheese) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return psData;
            }
        });
    }

    /**
     * Handles the RecvTotemAnchors packet.
     * @param player The player's instance.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvTotemAnchors(GamePlayer player, GameRoom playerRoom, NetByteArray psData) {
        if (playerRoom.getRoomType() == RoomType.TOTEM) {
            String[] values = new String(psData.toByteArray(), StandardCharsets.UTF_8).split(String.valueOf('\u0001'));
            String code = values[0];
            String x = values[1];
            String y = values[2];
            if ((int) player.tempTotem[0] < 20) {
                player.tempTotem[0] = ((int) player.tempTotem[0]) + 1;
                player.broadcastPacket(new PacketSendTotemItemCount((int) player.tempTotem[0]));
                player.tempTotem[1] += "#3#" + String.join("\u0001", String.valueOf(code), String.valueOf(x), String.valueOf(y));
            }
        }
    }

    /**
     * Handles when the player votes in the vote box.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvMapVote(GameRoom playerRoom, NetByteArray psData) {
        if (psData.getLength() > 0)
            playerRoom.receivedYesVotes++;
        else
            playerRoom.receivedNoVotes++;
    }

    /**
     * Handles when the player loads a map from the map editor.
     * @param player The player's instance.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvMapEditorLoadMap(GamePlayer player, GameRoom playerRoom, NetByteArray psData) {
        if (playerRoom.getRoomType() != RoomType.EDITOR)
            return;

        try {
            String mapCode = new String(psData.toByteArray());
            mapCode = mapCode.replace("@", "");
            MapEntry map = this.server.getDatabase().loadMapByCode(Integer.parseInt(mapCode));
            if (map == null) {
                player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorLoadMapError) {
                    @Override
                    public PSByteArray build() throws PSSendPacketException {
                        return new PSByteArray();
                    }
                });

                return;
            }

            if (!map.getMapAuthor().equals(player.getPlayerName()) && !player.hasStaffPermission(StaffRoleType.MAPCREW, false)) {
                player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorLoadMapError) {
                    @Override
                    public PSByteArray build() throws PSSendPacketException {
                        return new PSByteArray();
                    }
                });

                return;
            }

            playerRoom.mapEditorMapCode = map.getMapCode();
            playerRoom.mapEditorMapXml = map.mapXML;
            playerRoom.isMapEditorMapValidated = false;
            player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorLoadMap) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    NetByteArray packet = new NetByteArray();
                    packet.writeUTF(map.mapXML, false);
                    packet.writeByte(1);
                    packet.writeUTF(String.valueOf(map.mapYesVotes), false);
                    packet.writeByte(1);
                    packet.writeUTF(String.valueOf(map.mapNoVotes), false);
                    packet.writeByte(1);
                    packet.writeUTF(String.valueOf(map.mapCategory), false);
                    return packet;
                }
            });
        } catch (Exception ignored) {
            player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorLoadMapError) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    return new PSByteArray();
                }
            });
        }
    }

    /**
     * Handles when the player validates a map in the map editor.
     * @param player The player's instance.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvMapEditorValidateMap(GamePlayer player, GameRoom playerRoom, NetByteArray psData) {
        if (playerRoom.getRoomType() != RoomType.EDITOR)
            return;

        String xml = new String(psData.toByteArray());
        if (Utils.checkValidXML(xml)) {
            player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorEnterRoom) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    return new NetByteArray().writeByte(0);
                }
            });

            playerRoom.isMapEditorMapValidated = false;
            playerRoom.isMapEditorMapValidating = true;
            playerRoom.mapEditorMapXml = xml;
            playerRoom.sendChangeMap();
        }
    }

    /**
     * Handles when the player pastes the map XML in the map editor.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvMapEditorMapXml(GameRoom playerRoom, NetByteArray psData) {
        if (playerRoom.getRoomType() != RoomType.EDITOR)
            return;

        String xml = new String(psData.toByteArray());
        if (Utils.checkValidXML(xml))
            playerRoom.mapEditorMapXml = xml;
    }

    /**
     * Handles when the player returns back to the editor.
     * @param player The player's instance.
     * @param playerRoom The player's room.
     */
    private void handleRecvMapEditorReturnBack(GamePlayer player, GameRoom playerRoom) {
        if (playerRoom.getRoomType() != RoomType.EDITOR)
            return;

        playerRoom.isMapEditorMapValidating = false;
        player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorEnterRoom) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                NetByteArray packet = new NetByteArray();
                packet.writeByte(1);
                packet.writeByte(1);
                return packet;
            }
        });
    }

    /**
     * Handles when the player export a map from the map editor.
     * @param player The player's instance.
     * @param playerRoom The player's room.
     * @param psData The packet's data.
     */
    private void handleRecvMapEditorExportMap(GamePlayer player, GameRoom playerRoom, NetByteArray psData) {
        if (playerRoom.getRoomType() != RoomType.EDITOR)
            return;

        boolean isTribeHouse = psData.getLength() > 0;
        int mapCode;
        int mapCategory = (isTribeHouse ? 22 : 0);
        if (player.getPlayerData().cheeseCount < 1000) {
            player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorMessage) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    return new PSByteArray().writeByte(0);
                }
            });

            return;
        }


        if ((isTribeHouse && player.getPlayerData().shopCheese < 5) || player.getPlayerData().shopCheese < this.server.getConfig().getGameConfig().getMapEditorExportCheesePrice()) {
            player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorMessage) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    return new PSByteArray().writeByte(1).writeByte(1);
                }
            });

            return;
        }

        player.getPlayerData().shopCheese -= (isTribeHouse ? 5 : this.server.getConfig().getGameConfig().getMapEditorExportCheesePrice());
        if (playerRoom.mapEditorMapCode != -1) {
            mapCode = playerRoom.mapEditorMapCode;
            MapEntry map = this.server.getDatabase().loadMapByCode(mapCode);
            map.mapXML = playerRoom.mapEditorMapXml;
            this.server.getDatabase().saveMap(map);
        } else {
            MapEntry map = new MapEntry(this.server.getNextDatabaseCounterId("lastMapId"), player.getPlayerName(), mapCategory, playerRoom.mapEditorMapXml);
            mapCode = map.getMapCode();
            this.server.getDatabase().saveMap(map);
        }

        player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorEnterRoom) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return new NetByteArray().writeUTF("0", false);
            }
        });

        player.sendEnterRoom("", "");
        player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorMapExported) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return new NetByteArray().writeUTF(String.valueOf(mapCode), false);
            }
        });
    }

    /**
     * Handles when the player resets the map editor.
     * @param playerRoom The player's room.
     */
    private void handleRecvMapEditorResetMap(GameRoom playerRoom) {
        if (playerRoom.getRoomType() != RoomType.EDITOR)
            return;

        playerRoom.mapEditorMapCode = -1;
    }

    /**
     * Handles when the player exits from the map editor.
     * @param player The player's instance.
     */
    private void handleRecvMapEditorExit(GamePlayer player) {
        if (player.getPlayerRoom().getRoomType() != RoomType.EDITOR)
            return;

        player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorEnterRoom) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return new NetByteArray().writeUTF("0", false);
            }
        });

        player.sendEnterRoom("", "");
    }
}