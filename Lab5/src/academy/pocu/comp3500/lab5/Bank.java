package academy.pocu.comp3500.lab5;

public class Bank {
    private byte[][] bankPubkeys;
    private long[] bankAmounts;

    public Bank(byte[][] pubKeys, final long[] amounts) {
        this.bankPubkeys = pubKeys;
        this.bankAmounts = amounts;
    }

    public long getBalance(final byte[] pubKey) {
        if (bankPubkeys == null && pubKey == null) {
            return -1;
        }

        for (int i = 0; i < bankPubkeys.length; i++) {
            boolean isNoAccount = false;

            for (int j = 0; j < bankPubkeys[i].length; j++) {
                if (bankPubkeys[i][j] != pubKey[j]) {
                    isNoAccount = true;
                    break;
                }
            }

            if (isNoAccount == false) {
                return bankAmounts[i];
            }
        }

        return -1;
    }

    public boolean transfer(final byte[] from, byte[] to, final long amount, final byte[] signature) {
        return false;
    }
}