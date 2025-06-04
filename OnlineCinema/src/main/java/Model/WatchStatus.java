package Model;

public enum WatchStatus {
    NOT_WATCHING("Не смотрю"),
    PLAN_TO_WATCH("Буду смотреть"),
    WATCHING("Смотрю"),
    DROPPED("Брошено"),
    COMPLETED("Просмотрено");

    private final String label;
    WatchStatus(String label) { this.label = label; }
    public String getLabel() { return label; }

    public static WatchStatus fromLabel(String label) {
        for (WatchStatus ws : values()) {
            if (ws.getLabel().equalsIgnoreCase(label)) return ws;
        }
        return null;
    }
}
