package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

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

        TreeNode.setCount(count);
        TreeNode.setIndex(0);

        int length = playerCount < count ? playerCount : count;
        Player[] topPlayers = new Player[length];
        TreeNode.traverseReverse(root, topPlayers, 0);

        return topPlayers;
    }

    public Player[] getBottom(final int count) {
        if (playerCount <= 0 || count <= 0) {
            return new Player[0];
        }

        TreeNode.setCount(count);
        TreeNode.setIndex(0);

        int length = playerCount < count ? playerCount : count;
        Player[] bottomPlayers = new Player[length];
        TreeNode.traverseInorder(root, bottomPlayers, 0);

        return bottomPlayers;
    }

    public boolean join(final Player player) {
        return false;
    }

    public boolean leave(final Player player) {
        return false;
    }
}
