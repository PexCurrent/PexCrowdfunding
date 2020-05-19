package cn.qingwei.graduationproject.pojo;

public enum  ItemStatus {
    审核中(0, "审核中"),
    上线中(1, "上线中"),
    下线中(2, "下线中");


    private int id;
    private String itemStatus;

    ItemStatus(int id, String itemStatus) {
        this.id = id;
        this.itemStatus = itemStatus;
    }

    public static ItemStatus getEnumByid(int id) {
        for (ItemStatus s : ItemStatus.values()) {
            if (s.getId() == id)
                return s;
        }
        return null;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;


    }


}
