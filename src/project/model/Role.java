package project.model;

public class Role {
    private int id;
    private RoleName RoleName;

    public Role() {
    }

    public Role(int id, RoleName roleName) {
        this.id = id;
        RoleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return RoleName;
    }

    public void setRoleName(RoleName roleName) {
        RoleName = roleName;
    }
}
