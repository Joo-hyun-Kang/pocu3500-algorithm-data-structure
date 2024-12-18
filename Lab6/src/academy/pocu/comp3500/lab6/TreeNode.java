package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class TreeNode {
    private Player player;
    private TreeNode left;
    private TreeNode right;
    private static int count;
    private static int index;

    public TreeNode() {
    }

    public TreeNode(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public TreeNode getLeft() {
        return left;
    }

    public static TreeNode insertRecursive(TreeNode node, Player player) {
        if (node == null) {
            return new TreeNode(player);
        }

        if (player.getRating() < node.player.getRating()) {
            node.left = insertRecursive(node.left, player);
        } else {
            node.right = insertRecursive(node.right, player);
        }

        return node;
    }

    public static TreeNode getPlayerOrNull(TreeNode node, Player player, int approximation, TreeNode result) {
        if (node == null) {
            return result;
        }

        int difference = player.getRating() - node.player.getRating();

        int absDiff = difference > 0 ? difference : difference * -1;

        if (approximation == absDiff) {
            result = result.player.getRating() > node.player.getRating() ? result : node;
        }

        if (approximation > absDiff && absDiff != 0) {
            approximation = absDiff;
            result = node;
        }

        if ((player.getRating() == node.player.getRating())) {
            TreeNode leftRet = getPlayerOrNull(node.left, player, approximation, result);
            TreeNode rightRet = getPlayerOrNull(node.right, player, approximation, result);

            if (leftRet == null) {
                return rightRet;
            }

            if (rightRet == null) {
                return leftRet;
            }

            int leftRetDiff = getDiffAbs(leftRet.player.getRating(), player.getRating());

            int rightRetDiff = getDiffAbs(rightRet.player.getRating(), player.getRating());

            TreeNode value;
            if (leftRetDiff == rightRetDiff) {
                return value = leftRet.player.getRating() > rightRet.player.getRating() ? leftRet : rightRet;
            }

            return value = leftRetDiff < rightRetDiff ? leftRet : rightRet;

        } else if ((player.getRating() < node.player.getRating())) {
            return getPlayerOrNull(node.left, player, approximation, result);
        } else {
            return getPlayerOrNull(node.right, player, approximation, result);
        }
    }

    private static int getDiffAbs(int num1, int num2) {
        int difference = num1 - num2;

        int absDiff = difference > 0 ? difference : difference * -1;

        return absDiff;
    }

    public static void traverseReverse(TreeNode node, Player[] outPlayers, int levels) {
        if (node == null) {
            return;
        }

        traverseReverse(node.right, outPlayers, levels + 1);

        if (index < count) {
            outPlayers[index++] = node.player;
        }

        if (index >= count) {
            return;
        }

        traverseReverse(node.left, outPlayers, levels + 1);
    }

    public static void traverseInorder(TreeNode node, Player[] outPlayers, int levels) {
        if (node == null) {
            return;
        }

        traverseInorder(node.left, outPlayers, levels + 1);

        if (index < count) {
            outPlayers[index++] = node.player;
        }

        if (index >= count) {
            return;
        }

        traverseInorder(node.right, outPlayers, levels + 1);
    }

    public static void setCount(int count) {
        TreeNode.count = count;
    }

    public static void setIndex(int index) {
        TreeNode.index = index;
    }

    public static boolean joinRecursive(TreeNode node, Player player, TreeNode parent, Direction direction) {
        if (node == null) {
            if (direction == Direction.LEFT) {
                parent.left = new TreeNode(player);
            } else {
                parent.right = new TreeNode(player);
            }

            return true;
        }

        if (player.getRating() == node.player.getRating()) {
            if (player.getId() == player.getId()) {

                return false;
            }
        }

        boolean result = false;

        if (player.getRating() < node.player.getRating()) {
            result = joinRecursive(node.left, player, node, Direction.LEFT);
        } else {
            result = joinRecursive(node.right, player, node, Direction.RIGHT);
        }

        return result;
    }

    public static boolean leaveRecursive(TreeNode current, Player player, TreeNode parent, Direction direction) {
        if (current == null) {
            return false;
        }

        boolean result = false;

        if (player.getRating() == current.player.getRating()) {
            if (player.getId() == current.player.getId()) {

                if (current.left == null && current.right == null) {
                    if (direction == Direction.NONE) {
                        parent.getLeft().setPlayer(null);
                    } else if (direction == Direction.LEFT) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }

                    result = true;
                } else if (current.left != null && current.right == null) {
                    TreeNode tmp = searchInorderSuccessor(current.left, null);

                    current.setPlayer(tmp.getPlayer());

                    leaveRecursive(current.left, current.getPlayer(), current, Direction.LEFT);

                    result = true;
                } else if (current.left == null && current.right != null) {
                    TreeNode tmp = searchInorderPreSuccessor(current.right, null);

                    current.setPlayer(tmp.getPlayer());

                    leaveRecursive(current.right, current.getPlayer(), current, Direction.RIGHT);

                    result = true;
                } else {
                    TreeNode tmp = searchInorderPreSuccessor(current.right, null);

                    current.setPlayer(tmp.getPlayer());

                    leaveRecursive(current.right, current.getPlayer(), current, Direction.RIGHT);

                    result = true;
                }

                return result;
            }
        }

        if (player.getRating() < current.player.getRating()) {
            result = leaveRecursive(current.left, player, current, Direction.LEFT);
        } else {
            result = leaveRecursive(current.right, player, current, Direction.RIGHT);
        }

        return result;
    }

    private static TreeNode searchInorderSuccessor(TreeNode current, TreeNode parentOrNull) {
        if (current == null) {
            return parentOrNull;
        }

        return searchInorderSuccessor(current.right, current);
    }

    private static TreeNode searchInorderPreSuccessor(TreeNode current, TreeNode parentOrNull) {
        if (current == null) {
            return parentOrNull;
        }

        return searchInorderPreSuccessor(current.left, current);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
