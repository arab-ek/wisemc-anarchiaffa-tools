package pl.arab.TOOLS.utils;

public final class FormatUtil {
    private FormatUtil() {
    }

    public static String formatTime(int totalSeconds) {
        if (totalSeconds <= 0) {
            return "0sek";
        } else {
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            StringBuilder formattedTime = new StringBuilder();
            if (minutes > 0) {
                formattedTime.append(minutes).append("min");
            }

            if (seconds > 0) {
                if (minutes > 0) {
                    formattedTime.append(" ");
                }
                formattedTime.append(seconds).append("sek");
            }

            return formattedTime.toString();
        }
    }
}