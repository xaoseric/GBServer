package com.Gbserver.variables;

import org.bukkit.Bukkit;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Vaults {
    public static List<Vault> vaults = new LinkedList<Vault>();

    public static Vault getVault(UUID uuid) {
        Object[] v = vaults.toArray();
        for (Object vaul : v) {
            Vault vault = (Vault) vaul;
            if (vault.getUUID().equals(uuid)) {
                return vault;
            }
        }
        Vault va = new Vault(Bukkit.getPlayer(uuid));
        vaults.add(va);
        return va;
    }
}
