package academy.pocu.comp3500.lab5;

public class Bank {
    private byte[][] mPubkeys;
    private long[] mAmounts;

    public Bank(byte[][] pubKeys, final long[] amounts) {
        this.mPubkeys = pubKeys;
        this.mAmounts = amounts;
    }

    public long getBalance(final byte[] pubKey) {
        /*
        for (int i = 0; i < mPubkeys.length; i++) {
            boolean isNoAccount = false;

            for (int j = 0; j < mPubkeys[i].length; j++) {
                if (mPubkeys[i][j] !=  pubKey[j]) {
                    isNoAccount = true;
                    break;
                }
            }

            if (isNoAccount == false) {
                return mAmounts[i];
            }
        }
        */
        return -1;
    }

    public boolean transfer(final byte[] from, byte[] to, final long amount, final byte[] signature) {
        return false;
    }
}