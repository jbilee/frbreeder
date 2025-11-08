package com.frbreeder.app.domain.common;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public enum FrColor {

    MAIZE(1, 1, "Maize"),
    CREAM(163, 2, "Cream"),
    ANTIQUE(97, 3, "Antique"),
    WHITE(2, 4, "White"),
    MOON(74, 5, "Moon"),
    ICE(3, 6, "Ice"),
    ORCA(131, 7, "Orca"),
    PLATINUM(4, 8, "Platinum"),
    SILVER(5, 9, "Silver"),
    DUST(146, 10, "Dust"),
    GREY(6, 11, "Grey"),
    SMOKE(91, 12, "Smoke"),
    GLOOM(98, 13, "Gloom"),
    LEAD(118, 14, "Lead"),
    SHALE(177, 15, "Shale"),
    FLINT(129, 16, "Flint"),
    CHARCOAL(7, 17, "Charcoal"),
    COAL(8, 18, "Coal"),
    OILSICK(70, 19, "Oilsick"),
    BLACK(9, 20, "Black"),
    OBSIDIAN(10, 21, "Obsidian"),
    ELDRITCH(176, 22, "Eldritch"),
    MIDNIGHT(11, 23, "Midnight"),
    SHADOW(12, 24, "Shadow"),
    BLACKBERRY(127, 25, "Blackberry"),
    MULBERRY(13, 26, "Mulberry"),
    PLUM(92, 27, "Plum"),
    WISTERIA(119, 28, "Wisteria"),
    THISTLE(14, 29, "Thistle"),
    FOG(137, 30, "Fog"),
    MIST(150, 31, "Mist"),
    LAVENDER(15, 32, "Lavender"),
    HEATHER(68, 33, "Heather"),
    PURPLE(16, 34, "Purple"),
    ORCHID(69, 35, "Orchid"),
    AMETHYST(114, 36, "Amethyst"),
    NIGHTSHADE(175, 37, "Nightshade"),
    VIOLET(17, 38, "Violet"),
    GRAPE(147, 39, "Grape"),
    ROYAL(18, 40, "Royal"),
    EGGPLANT(111, 41, "Eggplant"),
    IRIS(82, 42, "Iris"),
    STORM(19, 43, "Storm"),
    TWILIGHT(174, 44, "Twilight"),
    INDIGO(112, 45, "Indigo"),
    SAPPHIRE(71, 46, "Sapphire"),
    NAVY(20, 47, "Navy"),
    COBALT(136, 48, "Cobalt"),
    ULTRAMARINE(90, 49, "Ultramarine"),
    BLUE(21, 50, "Blue"),
    PERIWINKLE(135, 51, "Periwinkle"),
    LAPIS(148, 52, "Lapis"),
    SPLASH(22, 53, "Splash"),
    CORNFLOWER(145, 54, "Cornflower"),
    SKY(23, 55, "Sky"),
    STONEWASH(24, 56, "Stonewash"),
    OVERCAST(126, 57, "Overcast"),
    STEEL(25, 58, "Steel"),
    DENIM(26, 59, "Denim"),
    ABYSS(96, 60, "Abyss"),
    PHTHALO(151, 61, "Phthalo"),
    AZURE(27, 62, "Azure"),
    CARIBBEAN(28, 63, "Caribbean"),
    TEAL(29, 64, "Teal"),
    CERULEAN(117, 65, "Cerulean"),
    CYAN(89, 66, "Cyan"),
    ROBIN(99, 67, "Robin"),
    AQUA(30, 68, "Aqua"),
    TURQUOISE(149, 69, "Turquoise"),
    SPRUCE(100, 70, "Spruce"),
    PISTACHIO(125, 71, "Pistachio"),
    SEAFOAM(31, 72, "Seafoam"),
    MINT(152, 73, "Mint"),
    JADE(32, 74, "Jade"),
    SPEARMINT(78, 75, "Spearmint"),
    THICKET(141, 76, "Thicket"),
    PEACOCK(134, 77, "Peacock"),
    EMERALD(33, 78, "Emerald"),
    SHAMROCK(80, 79, "Shamrock"),
    JUNGLE(34, 80, "Jungle"),
    HUNTER(81, 81, "Hunter"),
    FOREST(35, 82, "Forest"),
    CAMO(154, 83, "Camo"),
    ALGAE(153, 84, "Algae"),
    SWAMP(36, 85, "Swamp"),
    AVOCADO(37, 86, "Avocado"),
    GREEN(38, 87, "Green"),
    FERN(113, 88, "Fern"),
    MANTIS(79, 89, "Mantis"),
    PEAR(101, 90, "Pear"),
    LEAF(39, 91, "Leaf"),
    RADIOACTIVE(130, 92, "Radioactive"),
    HONEYDEW(102, 93, "Honeydew"),
    PERIDOT(144, 94, "Peridot"),
    CHARTREUSE(155, 95, "Chartreuse"),
    SPRING(40, 96, "Spring"),
    CROCODILE(173, 97, "Crocodile"),
    OLIVE(123, 98, "Olive"),
    MURK(142, 99, "Murk"),
    MOSS(115, 100, "Moss"),
    GOLDENROD(41, 101, "Goldenrod"),
    AMBER(103, 102, "Amber"),
    HONEY(93, 103, "Honey"),
    LEMON(42, 104, "Lemon"),
    YELLOW(104, 105, "Yellow"),
    GRAPEFRUIT(128, 106, "Grapefruit"),
    BANANA(43, 107, "Banana"),
    SANDDOLLAR(110, 108, "Sanddollar"),
    FLAXEN(139, 109, "Flaxen"),
    IVORY(44, 110, "Ivory"),
    BUTTERCUP(167, 111, "Buttercup"),
    GOLD(45, 112, "Gold"),
    METALS(140, 113, "Metals"),
    MARIGOLD(75, 114, "Marigold"),
    SUNSHINE(46, 115, "Sunshine"),
    SAFFRON(84, 116, "Saffron"),
    SUNSET(172, 117, "Sunset"),
    PEACH(105, 118, "Peach"),
    CANTALOUPE(171, 119, "Cantaloupe"),
    ORANGE(47, 120, "Orange"),
    BRONZE(83, 121, "Bronze"),
    TERRACOTTA(108, 122, "Terracotta"),
    CARROT(133, 123, "Carrot"),
    FIRE(48, 124, "Fire"),
    PUMPKIN(158, 125, "Pumpkin"),
    TANGERINE(49, 126, "Tangerine"),
    CINNAMON(77, 127, "Cinnamon"),
    CARAMEL(156, 128, "Caramel"),
    SAND(50, 129, "Sand"),
    TAN(76, 130, "Tan"),
    BEIGE(51, 131, "Beige"),
    STONE(52, 132, "Stone"),
    TAUPE(95, 133, "Taupe"),
    SLATE(53, 134, "Slate"),
    DRIFTWOOD(165, 135, "Driftwood"),
    LATTE(143, 136, "Latte"),
    DIRT(162, 137, "Dirt"),
    CLAY(106, 138, "Clay"),
    SABLE(138, 139, "Sable"),
    UMBER(157, 140, "Umber"),
    SOIL(54, 141, "Soil"),
    HICKORY(88, 142, "Hickory"),
    TARNISH(124, 143, "Tarnish"),
    GINGER(122, 144, "Ginger"),
    BROWN(55, 145, "Brown"),
    CHOCOLATE(56, 146, "Chocolate"),
    AUBURN(166, 147, "Auburn"),
    COPPER(94, 148, "Copper"),
    RUST(57, 149, "Rust"),
    TOMATO(58, 150, "Tomato"),
    VERMILION(169, 151, "Vermilion"),
    RUBY(86, 152, "Ruby"),
    CHERRY(116, 153, "Cherry"),
    CRIMSON(59, 154, "Crimson"),
    GARNET(161, 155, "Garnet"),
    SANGUINE(121, 156, "Sanguine"),
    BLOOD(60, 157, "Blood"),
    MAROON(61, 158, "Maroon"),
    BERRY(87, 159, "Berry"),
    RED(62, 160, "Red"),
    STRAWBERRY(168, 161, "Strawberry"),
    CERISE(132, 162, "Cerise"),
    CARMINE(63, 163, "Carmine"),
    BRICK(107, 164, "Brick"),
    CORAL(64, 165, "Coral"),
    BLUSH(159, 166, "Blush"),
    COTTONCANDY(164, 167, "Cottoncandy"),
    WATERMELON(120, 168, "Watermelon"),
    MAGENTA(65, 169, "Magenta"),
    FUCHSIA(170, 170, "Fuchsia"),
    RASPBERRY(160, 171, "Raspberry"),
    WINE(72, 172, "Wine"),
    MAUVE(73, 173, "Mauve"),
    PINK(66, 174, "Pink"),
    BUBBLEGUM(109, 175, "Bubblegum"),
    ROSE(67, 176, "Rose"),
    PEARL(85, 177, "Pearl");

    public static final int TOTAL_COLORS = 177;
    private static final Map<Integer, FrColor> CACHE = new HashMap<>();

    static {
        for (FrColor color : values()) {
            CACHE.put(color.getFrId(), color);
        }
    }

    private final int frId;
    private final int gradientOrder;
    private final String name;

    FrColor(final int frId, final int gradientOrder, final String name) {
        this.frId = frId;
        this.gradientOrder = gradientOrder;
        this.name = name;
    }

    public static FrColor findByFrId(final int id) {
        return CACHE.get(id);
    }

    public static List<String> getInnerColors(final int start, final int end) {
        return CACHE.values().stream()
                .filter(color -> color.gradientOrder >= start && color.gradientOrder <= end)
                .sorted(Comparator.comparing(FrColor::getGradientOrder))
                .map(color -> color.name)
                .toList();
    }

    public static List<String> getOuterColors(final int start, final int end) {
        List<String> startingHalf = CACHE.values().stream()
                .filter(color -> color.gradientOrder <= start)
                .sorted(Comparator.comparing(FrColor::getGradientOrder))
                .map(color -> color.name)
                .toList();

        List<String> endingHalf = CACHE.values().stream()
                .filter(color -> color.gradientOrder >= end)
                .sorted(Comparator.comparing(FrColor::getGradientOrder))
                .map(color -> color.name)
                .toList();

        return Stream.concat(endingHalf.stream(), startingHalf.stream())
                .toList();
    }

    public int getFrId() {
        return frId;
    }

    public int getGradientOrder() {
        return gradientOrder;
    }

    public String getName() {
        return name;
    }

}
