package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;
import static academy.pocu.comp3500.lab6.Direction.LEFT;


public class League {
    private TreeNode root;
    private int playerCount;

    public League() {
    }

    public League(Player[] players) {
        TreeNode root = new TreeNode(players[0]);

        for (int i = 1; i < players.length; i++) {
            TreeNode.insertRecursive(root, players[i]);
        }

        this.root = root;
        this.playerCount = players.length;
    }

    public Player findMatchOrNull(final Player player) {
        if (player == null || playerCount < 2) {
            return null;
        }

        return TreeNode.getPlayerOrNull(root, player, Integer.MAX_VALUE, null).getPlayer();
    }

    public Player[] getTop(final int count) {
        if (playerCount <= 0 || count <= 0) {
            return new Player[0];
        }

        int length = playerCount < count ? playerCount : count;
        TreeNode.setCount(length);
        TreeNode.setIndex(0);

        Player[] topPlayers = new Player[length];
        TreeNode.traverseReverse(root, topPlayers, 0);

        return topPlayers;
    }

    public Player[] getBottom(final int count) {
        if (playerCount <= 0 || count <= 0) {
            return new Player[0];
        }

        int length = playerCount < count ? playerCount : count;

        TreeNode.setCount(length);
        TreeNode.setIndex(0);

        Player[] bottomPlayers = new Player[length];
        TreeNode.traverseInorder(root, bottomPlayers, 0);

        return bottomPlayers;
    }

    public boolean join(final Player player) {
        if (player == null || playerCount < 1) {
            return false;
        }

        return TreeNode.joinRecursive(root, player, null, LEFT);
    }

    public boolean leave(final Player player) {
        return false;
    }
}
