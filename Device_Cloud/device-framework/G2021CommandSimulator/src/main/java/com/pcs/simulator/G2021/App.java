package com.pcs.simulator.G2021;

import java.util.Scanner;

import com.pcs.device.gateway.G2021.command.OperationCommand;
import com.pcs.device.gateway.G2021.command.PointDiscoveryRequest;
import com.pcs.device.gateway.G2021.command.PointSnapshotRequest;
import com.pcs.device.gateway.G2021.command.PointWrite;
import com.pcs.device.gateway.G2021.message.point.type.DataType;
import com.pcs.deviceframework.commandprocessor.processor.CommandProcessor;
import com.pcs.deviceframework.commandprocessor.processor.mode.ProcessorMode;
import com.pcs.deviceframework.commandprocessor.processor.util.CommandProcessorUtility;

/**
 * Hello world!
 *
 */
public class App {




	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);

		Boolean continueProcess = false;

		System.out.println(" G2021 Command Executor ");
		System.out.println("=========================");
		System.out.println();

		System.out.println("Enter unit id");
		int unitId = scanner.nextInt();
		System.out.println("Enter unit session id");
		int sessionId = scanner.nextInt();

		System.out.println(" Select Command To Fire ");
		System.out.println("=========================");
		System.out.println();

		do {
			System.out.println("||====================================||");
			System.out.println("|| 1. Point Discovery Request         ||");
			System.out.println("|| 2. System Command Request          ||");
			System.out.println("|| 3. Point Instant Snapshot Request  ||");
			System.out.println("|| 4. Point Write Command             ||");
			System.out.println("||====================================||");
			System.out.println();
			int choice = scanner.nextInt();
			CommandProcessor commandProcessor = null;

			switch (choice) {

			case 1:
				System.out.println("Point Discovery Request Selected !!");
				PointDiscoveryRequest discoveryReqCommand = new PointDiscoveryRequest();

				discoveryReqCommand.setUnitId(unitId);
				discoveryReqCommand.setSessionId(sessionId);			

				System.out.println("Enter lease time");
				discoveryReqCommand.setLeaseTime(scanner.nextInt());

				System.out.println("Enter request Id");
				discoveryReqCommand.setPdReqId(scanner.nextInt());

				commandProcessor = CommandProcessorUtility.getCommandProcessor(ProcessorMode.TCP.name());
				commandProcessor.execute(unitId,discoveryReqCommand.getServerMessage());

				System.out.println("Command Submitted !!");
				System.out.println("Do you want to continue (\"Y\" to continue / any key to exit.)");
				continueProcess = scanner.next().equalsIgnoreCase("y")?true:false;

				break;

			case 2:
				System.out.println("System Command Request Selected !!");
				OperationCommand command = new OperationCommand();

				command.setUnitId(unitId);
				command.setSessionId(sessionId);

				System.out.println(" Select command type ");
				System.out.println("======================");
				System.out.println();

				System.out.println("1. Reboot");
				System.out.println("2. Sync");
				System.out.println("3. App Save");

				command.setCommandId((byte) scanner.nextInt());

				commandProcessor = CommandProcessorUtility.getCommandProcessor(ProcessorMode.TCP.name());
				commandProcessor.execute(unitId,command.getServerMessage());

				System.out.println("Command Submitted !!");
				System.out.println("Do you want to continue (\"Y\" to continue / any key to exit.)");
				continueProcess = scanner.next().equalsIgnoreCase("y")?true:false;

				break;

			case 3:
				System.out.println("Point Instant Snapshot Request Selected !!");
				PointSnapshotRequest pointSnapshotRequest = new PointSnapshotRequest();

				pointSnapshotRequest.setUnitId(unitId);
				pointSnapshotRequest.setSessionId(sessionId);

				System.out.println("Enter required point id");
				pointSnapshotRequest.setpId(scanner.nextInt());

				commandProcessor = CommandProcessorUtility.getCommandProcessor(ProcessorMode.TCP.name());
				commandProcessor.execute(unitId,pointSnapshotRequest.getServerMessage());

				System.out.println("Command Submitted !!");
				System.out.println("Do you want to continue (\"Y\" to continue / any key to exit.)");
				continueProcess = scanner.next().equalsIgnoreCase("y")?true:false;

				break;

			case 4:
				System.out.println("Point Write Command Selected !!");
				PointWrite pointWrite = new PointWrite();

				pointWrite.setUnitId(unitId);
				pointWrite.setSessionId(sessionId);

				System.out.println(" Select the data type of the point ");
				System.out.println("====================================");

				System.out.println(" Select the data type of the point ");
				System.out.println("0000. Boolean");
				System.out.println("0001. Short");
				System.out.println("0010. Integer");
				System.out.println("0011. Long");
				System.out.println("0100. Float");
				System.out.println("0101 . Timestamp");
				System.out.println("0110. String");
				System.out.println("0111. Text");

				String dataTypeChoice = scanner.next();
				DataType dataType = DataType.valueOfType(dataTypeChoice);
				pointWrite.setDataType(dataType);
				if(dataType == null){
					System.out.println("Invalid choice !!!");
					System.out.println("Do you want to continue (\"Y\" to continue / any key to exit.)");
					continueProcess = scanner.next().equalsIgnoreCase("y")?true:false;
					break;
				}
				switch (dataType) {
				case BOOLEAN:
					System.out.println("Enter new boolean value to be written");
					pointWrite.setData(scanner.nextBoolean());
					break;
					
				case SHORT:
					System.out.println("Enter new short value to be written");
					pointWrite.setData(scanner.nextShort());
					break;
					
				case INT:
					System.out.println("Enter new integer value to be written");
					pointWrite.setData(scanner.nextInt());
					break;
					
				case LONG:
					System.out.println("Enter new long value to be written");
					pointWrite.setData(scanner.nextLong());
					break;
					
				case FLOAT:
					System.out.println("Enter new float value to be written");
					pointWrite.setData(scanner.nextFloat());
					break;
					
				case TIMESTAMP:
					System.out.println("Enter new timestamp as UTC seconds(integer) value to be written");
					pointWrite.setData(scanner.nextInt());
					break;
					
				case STRING:
					System.out.println("Enter new String value to be written");
					pointWrite.setData(scanner.nextLine());
					break;
					
				case TEXT:
					System.out.println("Enter new text value to be written");
					pointWrite.setData(scanner.nextLine());
					break;
					
				default:
					System.out.println("Invalid choice !!!");
					System.out.println("Do you want to continue (\"Y\" to continue / any key to exit.)");
					continueProcess = scanner.next().equalsIgnoreCase("y")?true:false;
					break;
				}


				System.out.println("Enter Point Id");
				pointWrite.setpId(scanner.nextInt());

				System.out.println("Enter command priority (0 - 15)");
				pointWrite.setPriority(scanner.nextInt());

				commandProcessor = CommandProcessorUtility.getCommandProcessor(ProcessorMode.TCP.name());
				commandProcessor.execute(unitId,pointWrite.getServerMessage());

				System.out.println("Command Submitted !!");
				System.out.println("Do you want to continue (\"Y\" to continue / any key to exit.)");
				continueProcess = scanner.next().equalsIgnoreCase("y")?true:false;

				break;

			default:
				System.out.println("Invalid choice !!!");
				System.out.println("Do you want to continue (\"Y\" to continue / any key to exit.)");
				continueProcess = scanner.next().equalsIgnoreCase("y")?true:false;
				break;
			}
		} while (continueProcess);

		scanner.close();
		scanner = null;
		System.exit(0);
	}

}
