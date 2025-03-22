package UserAuth;

public class User {
    private int easyRecord, mediumRecord, hardRecord;
    private String name;

    public User(String name, int easyRecord, int mediumRecord, int hardRecord) {
        this.name = name;
        this.easyRecord = easyRecord;
        this.mediumRecord = mediumRecord;
        this.hardRecord = hardRecord;
    }

    public int getEasyRecord() {
        return easyRecord;
    }

    public int getMediumRecord() {
        return mediumRecord;
    }

    public int getHardRecord() {
        return hardRecord;
    }

    public String getName() {
        return name;
    }

}
