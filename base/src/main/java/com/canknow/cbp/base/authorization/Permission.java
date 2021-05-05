package com.canknow.cbp.base.authorization;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Permission {
    private Permission parent;
    private String name;
    private boolean isGrantedByDefault;
    private List<Permission> children;

    public String getFullName () {
        String fullName = this.name;
        Permission parent = this.parent;
        while (parent!=null) {
            fullName = parent.getName() + "." + this.name;
            parent = parent.getParent();
        }
        return fullName;
    }

    public Permission(String name, boolean isGrantedByDefault) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }
        this.name = name;
        this.isGrantedByDefault= isGrantedByDefault;
        this.children = new ArrayList<>();
    }

    public Permission(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public Permission createChildPermission(String name) {
        Permission permission = new Permission(name);
        permission.parent = this;
        this.children.add(permission);
        return permission;
    }

    public void removeChildPermission(String name) {
        children.removeIf(p -> p.name.equals(name));
    }

    @Override
    public String toString() {
        return String.format("[Permission: {0}]", name);
    }
}
