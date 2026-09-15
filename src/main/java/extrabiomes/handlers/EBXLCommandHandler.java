package extrabiomes.handlers;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

import extrabiomes.blocks.BlockCustomSapling;
import extrabiomes.blocks.BlockNewSapling;
import extrabiomes.lib.Element;
import extrabiomes.lib.Vector3;
import extrabiomes.module.summa.worldgen.WorldGenAcacia;
import extrabiomes.module.summa.worldgen.WorldGenAutumnTree;
import extrabiomes.module.summa.worldgen.WorldGenAutumnTree.AutumnTreeType;
import extrabiomes.module.summa.worldgen.WorldGenBaldCypressTree;
import extrabiomes.module.summa.worldgen.WorldGenBigAutumnTree;
import extrabiomes.module.summa.worldgen.WorldGenCypressTree;
import extrabiomes.module.summa.worldgen.WorldGenFirTree;
import extrabiomes.module.summa.worldgen.WorldGenFirTreeHuge;
import extrabiomes.module.summa.worldgen.WorldGenJapaneseMapleShrub;
import extrabiomes.module.summa.worldgen.WorldGenJapaneseMapleTree;
import extrabiomes.module.summa.worldgen.WorldGenLegendOak;
import extrabiomes.module.summa.worldgen.WorldGenNewRedwood;
import extrabiomes.module.summa.worldgen.WorldGenRainbowEucalyptusTree;
import extrabiomes.module.summa.worldgen.WorldGenSakuraBlossomTree;

public class EBXLCommandHandler extends CommandBase {

    @Override
    public String getCommandName() {
        return "ebxl";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "/ebxl help";
    }

    private void sendChatMessage(EntityPlayer player, String message) {
        player.addChatMessage(new ChatComponentText(message));
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] cmds) {
        if (icommandsender instanceof EntityPlayer player) {
            if (cmds.length == 0) {
                sendChatMessage(player, "use \"/ebxl help\" for the list of valid commands.");
            } else {
                switch (cmds[0]) {
                    case "help" -> {
                        if (cmds.length == 1) {
                            helpList(player);
                        } else {
                            // Give instructions about a command
                            switch (cmds[1]) {
                                case "help" -> {
                                    sendChatMessage(player, "§2-ExtrabiomesXl help Command-§r");
                                    sendChatMessage(player, "§o/ebxl help [command]§r");
                                    sendChatMessage(
                                        player,
                                        "If [command] is blank or an invalid command then the list of");
                                    sendChatMessage(
                                        player,
                                        "valid commands will be displayed. If [command] is a valid");
                                    sendChatMessage(
                                        player,
                                        "command then details about that command will be dispalyed.");
                                }
                                case "spawntree" -> {
                                    sendChatMessage(player, "§2-ExtrabiomesXl spawntree Command-§r");
                                    sendChatMessage(player, "§o/ebxl spawntree <treetype> <x> <y> <z> [seed]§r");
                                    sendChatMessage(player, "Forces a tree of the specified <type> to spawn at");
                                    sendChatMessage(player, "<x>,<y>,<z> in the world. [command] is optional and if a");
                                    sendChatMessage(
                                        player,
                                        "number is provided will force the tree to use the same random");
                                    sendChatMessage(
                                        player,
                                        "numbers for tree generation for any giver seed. For example");
                                    sendChatMessage(
                                        player,
                                        "\"/ebxl spawntree fur 0 100 0 100\" will cause the exact same");
                                    sendChatMessage(
                                        player,
                                        "fur tree to spawn at 0,100,0 no matter how many times you run");
                                    sendChatMessage(player, "the command.");
                                }
                                case "version" -> {
                                    sendChatMessage(player, "§2-ExtrabiomesXl version Command-§r");
                                    sendChatMessage(player, "§o/ebxl version§r");
                                    sendChatMessage(player, "Displays the change log for the current");
                                    sendChatMessage(player, "version of ExtrabiomesXL.");
                                }
                                case "lastseed" -> {
                                    sendChatMessage(player, "§2-ExtrabiomesXl lastseed Command-§r");
                                    sendChatMessage(player, "§o/ebxl lastseed <treetype>§r");
                                    sendChatMessage(
                                        player,
                                        "Displays the last random number that was used to generate");
                                    sendChatMessage(
                                        player,
                                        "the specified tree type for use with the spawntree command.");
                                }
                                case "saplingdespawntime" -> {
                                    sendChatMessage(player, "§2-ExtrabiomesXl saplingdespawntime Command-§r");
                                    sendChatMessage(player, "§o/ebxl saplingdespawntime [ticks]§r");
                                    sendChatMessage(
                                        player,
                                        "Display/set the number of ticks that a sapling will exist");
                                    sendChatMessage(player, "on the ground before it despawns.");
                                }
                                case "killtree" -> {
                                    sendChatMessage(player, "§2-ExtrabiomesXl killtree Command-§r");
                                    sendChatMessage(player, "§o/ebxl killtree <x> <y> <z>§r");
                                    sendChatMessage(player, "Kills the tree at the specified coords.");
                                }
                                default -> helpList(player);
                            }
                        }
                    }
                    case "kill" -> killTree(player, 0, 4, 0);
                    case "spawn" -> (new WorldGenSakuraBlossomTree(true))
                        .generate(player.worldObj, player.worldObj.rand, 0, 4, 0);
                    case "killtree" -> {
                        if (cmds.length == 4) {
                            try {
                                int x = Integer.parseInt(cmds[1]);
                                int y = Integer.parseInt(cmds[2]);
                                int z = Integer.parseInt(cmds[3]);

                                killTree(player, x, y, z);
                            } catch (Exception e) {
                                sendChatMessage(player, "X, Y and Z must be valid numbers.");
                            }

                        } else if (cmds.length == 2 && cmds[1].equals("here")) {
                            killTree(player, (int) player.posX, (int) player.posY - 1, (int) player.posZ);
                        } else {
                            sendChatMessage(player, "Incorrect format. /ebxl killtree <x> <y> <z>");
                        }
                    }
                    case "saplingdespawntime" -> {
                        if (cmds.length == 1) {
                            sendChatMessage(
                                player,
                                "Sapling despawn time is currently: " + BlockCustomSapling.getSaplingLifespan()
                                    + " ticks");
                        } else if (cmds.length == 2) {
                            int newTime = Integer.parseInt(cmds[1]);

                            if (newTime >= 0 && newTime <= 10000) {
                                BlockCustomSapling.setSaplingLifespan(newTime);
                                BlockNewSapling.setSaplingLifespan(newTime);
                                sendChatMessage(player, "Sapling despawn time set to: " + cmds[1] + " ticks");
                            } else {
                                sendChatMessage(player, "Sapling despawn time must be between 0 and 10000.");
                            }
                        } else {
                            sendChatMessage(player, "Incorrect format. /ebxl saplingDespawn [newtime]");
                        }
                    }
                    case "lastseed" -> {
                        if (cmds.length == 1) {
                            treeNames(player);
                        } else {
                            switch (cmds[1]) {
                                case "acacia" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenAcacia.getLastSeed());
                                case "cypress" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenCypressTree.getLastSeed());
                                case "baldcypress" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenBaldCypressTree.getLastSeed());
                                case "rainbow" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenRainbowEucalyptusTree.getLastSeed());
                                case "japanesemaple" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenJapaneseMapleTree.getLastSeed());
                                case "japanesemapleshrub" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenJapaneseMapleShrub.getLastSeed());
                                case "fir" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenFirTree.getLastSeed());
                                case "redwood" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenNewRedwood.getLastSeed());
                                case "largefir" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenFirTreeHuge.getLastSeed());
                                case "brown", "orange", "red", "yellow" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenAutumnTree.getLastSeed());
                                case "largebrown", "largeorange", "largered", "largeyellow" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenBigAutumnTree.getLastSeed());
                                case "sakura" -> sendChatMessage(
                                    player,
                                    "The last seed used was: " + WorldGenSakuraBlossomTree.getLastSeed());
                                case "legend" -> sendChatMessage(
                                    player,
                                    "The Legend Oak does not currently support seeding.");
                                default -> treeNames(player);
                            }
                        }
                    }
                    case "spawntree" -> {
                        if (cmds.length == 5) {
                            try {
                                int x = Integer.parseInt(cmds[2]);
                                int y = Integer.parseInt(cmds[3]);
                                int z = Integer.parseInt(cmds[4]);

                                switch (cmds[1]) {
                                    case "acacia" -> (new WorldGenAcacia(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "cypress" -> (new WorldGenCypressTree(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "baldcypress" -> (new WorldGenBaldCypressTree(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "rainbow" -> (new WorldGenRainbowEucalyptusTree(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "japanesemaple" -> (new WorldGenJapaneseMapleTree(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "japanesemapleshrub" -> (new WorldGenJapaneseMapleShrub(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "fir" -> (new WorldGenFirTree(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "redwood" -> (new WorldGenNewRedwood(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "largeFir" -> (new WorldGenFirTreeHuge(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "brown" -> {
                                        WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.BROWN);
                                        WorldGenAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    }
                                    case "orange" -> {
                                        WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.ORANGE);
                                        WorldGenAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    }
                                    case "red" -> {
                                        WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.PURPLE);
                                        WorldGenAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    }
                                    case "yellow" -> {
                                        WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.YELLOW);
                                        WorldGenAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    }
                                    case "largebrown" -> {
                                        WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(
                                            true,
                                            AutumnTreeType.BROWN);
                                        WorldGenBigAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    }
                                    case "largeorange" -> {
                                        WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(
                                            true,
                                            AutumnTreeType.ORANGE);
                                        WorldGenBigAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    }
                                    case "largered" -> {
                                        WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(
                                            true,
                                            AutumnTreeType.PURPLE);
                                        WorldGenBigAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    }
                                    case "largeyellow" -> {
                                        WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(
                                            true,
                                            AutumnTreeType.YELLOW);
                                        WorldGenBigAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    }
                                    case "sakura" -> (new WorldGenSakuraBlossomTree(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    case "legend" -> (new WorldGenLegendOak(true))
                                        .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                    default -> treeNames(player);
                                }
                            } catch (Exception e) {
                                sendChatMessage(player, "X, Y and Z must be valid numbers.");
                            }
                        } else if (cmds.length == 6) {
                            try {
                                int x = Integer.parseInt(cmds[2]);
                                int y = Integer.parseInt(cmds[3]);
                                int z = Integer.parseInt(cmds[4]);
                                long seed = Long.parseLong(cmds[5]);

                                switch (cmds[1]) {
                                    case "acacia" -> (new WorldGenAcacia(true))
                                        .generate(player.worldObj, seed, x, y, z);
                                    case "cypress" -> (new WorldGenCypressTree(true))
                                        .generate(player.worldObj, seed, x, y, z);
                                    case "baldcypress" -> (new WorldGenBaldCypressTree(true))
                                        .generate(player.worldObj, seed, x, y, z);
                                    case "rainbow" -> (new WorldGenRainbowEucalyptusTree(true))
                                        .generate(player.worldObj, seed, x, y, z);
                                    case "japanesemaple" -> (new WorldGenJapaneseMapleTree(true))
                                        .generate(player.worldObj, seed, x, y, z);
                                    case "japanesemapleshrub" -> (new WorldGenJapaneseMapleShrub(true))
                                        .generate(player.worldObj, seed, x, y, z);
                                    case "fir" -> (new WorldGenFirTree(true)).generate(player.worldObj, seed, x, y, z);
                                    case "redwood" -> (new WorldGenNewRedwood(true))
                                        .generate(player.worldObj, seed, x, y, z);
                                    case "largeFir" -> (new WorldGenFirTreeHuge(true))
                                        .generate(player.worldObj, seed, x, y, z);
                                    case "brown" -> {
                                        WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.BROWN);
                                        WorldGenAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, seed, x, y, z);
                                    }
                                    case "orange" -> {
                                        WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.ORANGE);
                                        WorldGenAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, seed, x, y, z);
                                    }
                                    case "red" -> {
                                        WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.PURPLE);
                                        WorldGenAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, seed, x, y, z);
                                    }
                                    case "yellow" -> {
                                        WorldGenAutumnTree tree = new WorldGenAutumnTree(true, AutumnTreeType.YELLOW);
                                        WorldGenAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, seed, x, y, z);
                                    }
                                    case "largeBrown" -> {
                                        WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(
                                            true,
                                            AutumnTreeType.BROWN);
                                        WorldGenBigAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, seed, x, y, z);
                                    }
                                    case "largeorange" -> {
                                        WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(
                                            true,
                                            AutumnTreeType.ORANGE);
                                        WorldGenBigAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, seed, x, y, z);
                                    }
                                    case "largered" -> {
                                        WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(
                                            true,
                                            AutumnTreeType.PURPLE);
                                        WorldGenBigAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, seed, x, y, z);
                                    }
                                    case "largeyellow" -> {
                                        WorldGenBigAutumnTree tree = new WorldGenBigAutumnTree(
                                            true,
                                            AutumnTreeType.YELLOW);
                                        WorldGenBigAutumnTree.setTrunkBlock(
                                            Block.getBlockFromItem(
                                                Element.LOG_AUTUMN.get()
                                                    .getItem()),
                                            Element.LOG_AUTUMN.get()
                                                .getItemDamage());
                                        tree.generate(player.worldObj, seed, x, y, z);
                                    }
                                    case "sakura" -> (new WorldGenSakuraBlossomTree(true))
                                        .generate(player.worldObj, seed, x, y, z);
                                    case "legend" -> {
                                        (new WorldGenLegendOak(true))
                                            .generate(player.worldObj, player.worldObj.rand, x, y, z);
                                        sendChatMessage(player, "The Legend Oak does not currently support seeding.");
                                    }
                                    default -> treeNames(player);
                                }
                            } catch (Exception e) {
                                sendChatMessage(player, "X, Y, Z and seed must be valid numbers.");
                            }
                        } else {
                            sendChatMessage(player, "Incorrect format. /ebxl spawntree <treetype> <x> <y> <z> [seed]");
                        }
                    }
                    default -> {
                        sendChatMessage(player, "\"/ebxl " + cmds[0] + "\" is not a valid command.");
                        sendChatMessage(player, "Use \"/ebxl help\" for the list of valid commands.");
                    }
                }
            }
        }

    }

    private void treeNames(EntityPlayer player) {
        sendChatMessage(player, "Only the following tree names are supported:");
        sendChatMessage(player, "acacia, cypress, fir, redwood, largeFir,");
        sendChatMessage(player, "brown, orange, red, yellow, largebrown,");
        sendChatMessage(player, "largeorange, largered, largeyellow, baldcypress,");
        sendChatMessage(player, "rainbow, japanesemaple, japanesemaple, japanesemapleshrub,");
        sendChatMessage(player, "sakura, legend");
    }

    private boolean killTree(EntityPlayer player, int x, int y, int z) {
        Queue<Vector3> killList = new LinkedList<>();

        killList.add(new Vector3(x, y, z));
        Vector3 currentBlock;

        while (!killList.isEmpty()) {
            currentBlock = killList.remove();
            Block block = player.worldObj.getBlock(currentBlock.x(), currentBlock.y(), currentBlock.z());
            int damage = player.worldObj.getBlockMetadata(currentBlock.x(), currentBlock.y(), currentBlock.z());
            String blockType = OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(block, 1, damage)));

            // shorten the coords
            int x1 = currentBlock.x();
            int y1 = currentBlock.y();
            int z1 = currentBlock.z();

            if (blockType.equals("logWood") || blockType.equals("treeLeaves")) {
                // Add extra blocks to the list
                killList.add(new Vector3(x1, y1 + 1, z1));
                killList.add(new Vector3(x1, y1 - 1, z1));
                killList.add(new Vector3(x1 + 1, y1, z1));
                killList.add(new Vector3(x1 - 1, y1, z1));
                killList.add(new Vector3(x1, y1, z1 + 1));
                killList.add(new Vector3(x1, y1, z1 - 1));

                // Remove the block
                player.worldObj.setBlockToAir(x1, y1, z1);
            }
        }

        return true;
    }

    private void helpList(EntityPlayer player) {
        // List the available commands
        sendChatMessage(player, "§2-ExtrabiomesXl Commands-§r");
        sendChatMessage(player, "/ebxl help [command]");
        sendChatMessage(player, "/ebxl lastseed <treetype>");
        sendChatMessage(player, "/ebxl killtree <x> <y> <z>");
        sendChatMessage(player, "/ebxl saplingdespawntime [ticks]");
        sendChatMessage(player, "/ebxl spawntree <treetype> <x> <y> <z> [seed]");
        sendChatMessage(player, "/ebxl version");
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
