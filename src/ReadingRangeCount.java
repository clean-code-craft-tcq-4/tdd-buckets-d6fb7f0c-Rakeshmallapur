import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadingRangeCount {
  
  

  public static String processReadings(List<Integer> inputReadings) {
    List<Integer> rangelist = new ArrayList<>();
    String outputMessage = "";
    if (inputReadings.isEmpty()) {
      return "invalid readings";
    }
    List<Integer> currentReadings =convertToAbsolute(inputReadings);
    Collections.sort(currentReadings);
    rangelist.add(currentReadings.get(0));
    for (int i = 1; i < currentReadings.size(); i++) {

      outputMessage = detectRanges(currentReadings, rangelist, i, outputMessage);

      outputMessage = checkForLastReading(currentReadings, rangelist, i, outputMessage);

    }
    return outputMessage.trim();
  }

  public static String printmessage(String message, List<Integer> range) {
    return message + range.get(0).toString() + "-" + range.get(range.size() - 1).toString() + "," + range.size() + "\n";
  }

  public static void clearList(List<Integer> currentReadings, List<Integer> range, int index) {
    range.clear();
    range.add(currentReadings.get(index));
  }
  
  public static String detectRanges(List<Integer> currentReadings, List<Integer> rangeList, int index, String message) {
    String localMessage = message;
    if (currentReadings.get(index).equals(currentReadings.get(index - 1)) ||
        currentReadings.get(index).equals(currentReadings.get(index - 1) + 1)) {
      rangeList.add(currentReadings.get(index));
    }
    else if (rangeList.size() > 1) {
      localMessage = formMessage(message, rangeList);
      clearList(currentReadings, rangeList, index);
    }
    else {
      clearList(currentReadings, rangeList, index);
    }
    return localMessage;
  }

  public static String checkForLastReading(List<Integer> currentReadings, List<Integer> rangeList, int index,
      String message) {
    String localMessage = message;
    if (index == currentReadings.size() - 1 && rangeList.size() > 1) {
      localMessage = formMessage(message, rangeList);
      clearList(currentReadings, rangeList, index);
    }
    return localMessage;
  }

  public static List<Integer> convert12BitToAmps(List<Integer> bitReadings) {
    List<Integer> ampReadings = new ArrayList<>();
    for (Integer reading : bitReadings) {
      if (!checkforValidReading(12, reading)) {
        return new ArrayList<>();
      }
      float f = 10 * (float) reading / 4094;
      ampReadings.add(Math.round(f));
    }
    return ampReadings;
  }

  public static List<Integer> convert10BitToAmps(List<Integer> bitReadings) {
    List<Integer> ampReadings = new ArrayList<>();
    for (Integer reading : bitReadings) {
      if (!checkforValidReading(10, reading)) {
        return new ArrayList<>();
      }
      float f = (float) convertToSignedReading((float) reading / 511);
      ampReadings.add(Math.round(f));
    }
    return ampReadings;
  }


  public static double convertToSignedReading(float reading) {
    if (reading < 1) {
      return -15 * (1 - reading);
    }
    return 15 * (reading - 1);

  }

  public static boolean checkforValidReading(int bit, int reading) {
    if (bit == 12) {
      return (reading > 0 && reading < 4095);
    }
      return (reading > 0 && reading < 1023);
  }

  public static List<Integer> convertToAmps(int bits, List<Integer> bitReadings) {
    if (bits == 10) {
      return convert10BitToAmps(bitReadings);
    }
    else if (bits == 12) {
      return convert12BitToAmps(bitReadings);
    }
    else {
      return new ArrayList<>();
    }

  }

  public static List<Integer> convertToAbsolute(List<Integer> inputReadings){
    List<Integer> currentReadings = new ArrayList<>();
    for(int reading :inputReadings) {
      currentReadings.add(Math.abs(reading));
    }
    return currentReadings;
  }

  
}
