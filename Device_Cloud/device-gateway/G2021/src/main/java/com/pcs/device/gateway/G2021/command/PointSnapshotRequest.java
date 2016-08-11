/**
 * 
 */
package com.pcs.device.gateway.G2021.command;

import java.nio.ByteBuffer;

import com.pcs.device.gateway.G2021.message.type.MessageType;
import com.pcs.device.gateway.G2021.packet.type.PacketType;

/**
 * @author pcseg171
 *
 */
public class PointSnapshotRequest {
	
	private static final PacketType PACKET_TYPE = PacketType.ANONYMOUS;
	private static final MessageType MESSAGE_TYPE = MessageType.POINTINSTANTSNAPSHOTREQUEST;

	public static byte[] getServerMessage(G2021Command g2021Command) throws Exception {
		ByteBuffer pointSnapshotRequest = ByteBuffer.allocate(14);
		pointSnapshotRequest.put(PACKET_TYPE.getType().byteValue());
		pointSnapshotRequest.put(MESSAGE_TYPE.getType().byteValue());
		pointSnapshotRequest.putInt(g2021Command.getSessionId());
		pointSnapshotRequest.putInt(g2021Command.getUnitId());
		pointSnapshotRequest.putShort(g2021Command.getPointId().shortValue());
		pointSnapshotRequest.putShort(g2021Command.getRequestId());
		return pointSnapshotRequest.array();
	}

}
