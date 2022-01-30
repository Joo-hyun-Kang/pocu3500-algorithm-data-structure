package academy.pocu.comp3500.lab4;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;

public final class Cracker {
    User[] userTable;
    String email;
    String password;

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        this.email = email;
        this.password = password;
    }

    public String[] run(final RainbowTable[] rainbowTables) {
        String[] plainPassword = new String[userTable.length];

        for (int i = 0; i < userTable.length; i++) {
            for (int j = 0; j < rainbowTables.length; j++) {
                if (rainbowTables[j].contains(userTable[i].getPasswordHash())) {
                    plainPassword[i] = rainbowTables[j].get(userTable[i].getPasswordHash());
                } else {
                    plainPassword[i] = null;
                }
            }
        }

        return plainPassword;
    }
}