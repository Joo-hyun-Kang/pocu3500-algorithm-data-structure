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

        boolean isNotNull = false;
        for (int i = 0; i < rainbowTables.length; i++) {
            for (int j = 0; j < userTable.length; j++) {
                if (rainbowTables[i].contains(userTable[j].getPasswordHash())) {
                    plainPassword[j] = rainbowTables[i].get(userTable[j].getPasswordHash());
                    isNotNull = true;
                } else {
                    plainPassword[j] = null;
                }
            }

            if (isNotNull == true) {
                break;
            }
        }
        
        return plainPassword;
    }
}