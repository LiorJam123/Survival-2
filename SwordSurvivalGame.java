import java.util.Random;
import java.util.Scanner;

class Character {
    String name;
    int health;
    int attackPower;
    Random random = new Random();

    public Character(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int attack(Character other) {
        int damage = random.nextInt(attackPower) + 1; // damage between 1 and attackPower
        other.health -= damage;
        return damage;
    }
}

public class SwordSurvivalGame {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void intro() {
        System.out.println("Welcome, brave warrior!");
        System.out.println("Your village has been invaded by dark forces.");
        System.out.println("You must survive wave after wave of enemies, fighting them with your sword.");
        System.out.println("Defeat the boss of each level to advance and save your land!\n");
        System.out.print("Press Enter to start your journey...");
        scanner.nextLine();
    }

    public static boolean fight(Character player, Character enemy) {
        System.out.println("\nA wild " + enemy.name + " appears with " + enemy.health + " HP!");
        while (player.isAlive() && enemy.isAlive()) {
            System.out.print("Do you want to (A)ttack or (R)un? ");
            String action = scanner.nextLine().toLowerCase();

            if (action.equals("a")) {
                int damage = player.attack(enemy);
                System.out.println("You hit the " + enemy.name + " for " + damage + " damage.");
                if (!enemy.isAlive()) {
                    System.out.println("You defeated the " + enemy.name + "!");
                    return true;
                }
                damage = enemy.attack(player);
                System.out.println("The " + enemy.name + " hits you for " + damage + " damage.");
                if (!player.isAlive()) {
                    System.out.println("You have been defeated... Game Over.");
                    return false;
                }
            } else if (action.equals("r")) {
                if (random.nextDouble() > 0.5) {
                    System.out.println("You successfully escaped!");
                    return true;
                } else {
                    System.out.println("You failed to escape!");
                    int damage = enemy.attack(player);
                    System.out.println("The " + enemy.name + " hits you for " + damage + " damage.");
                    if (!player.isAlive()) {
                        System.out.println("You have been defeated... Game Over.");
                        return false;
                    }
                }
            } else {
                System.out.println("Invalid action. Please choose again.");
            }
        }
        return player.isAlive();
    }

    public static boolean level(Character player, int levelNum) {
        System.out.println("\n--- Level " + levelNum + " ---");

        Character[] enemies = {
            new Character("Goblin", 10 + levelNum * 2, 3 + levelNum),
            new Character("Skeleton", 12 + levelNum * 3, 4 + levelNum),
            new Character("Orc", 15 + levelNum * 4, 5 + levelNum)
        };

        for (Character enemy : enemies) {
            if (!fight(player, enemy)) {
                return false;
            }
        }

        Character boss = new Character("Level " + levelNum + " Boss", 30 + levelNum * 10, 7 + levelNum * 2);
        System.out.println("\nBoss fight! You face " + boss.name + "!");
        if (!fight(player, boss)) {
            return false;
        }

        System.out.println("Congratulations! You cleared Level " + levelNum + "!\n");
        return true;
    }

    public static void game() {
        intro();
        Character player = new Character("Hero", 50, 7);
        int maxLevels = 3;

        for (int levelNum = 1; levelNum <= maxLevels; levelNum++) {
            if (!level(player, levelNum)) {
                System.out.println("Try again next time, warrior.");
                return;
            } else {
                player.health = Math.min(50, player.health + 20);
                System.out.println("Your health is restored a bit to " + player.health + " HP.");
            }
        }

        System.out.println("You have saved your village! You are a true hero!");
    }

    public static void main(String[] args) {
        game();
        scanner.close();
    }
}
