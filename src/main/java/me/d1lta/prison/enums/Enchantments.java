package me.d1lta.prison.enums;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enchants.book.ArchaeologyBook;
import me.d1lta.prison.enchants.book.CatGraceBook;
import me.d1lta.prison.enchants.book.FieryBook;
import me.d1lta.prison.enchants.book.HammerBook;
import me.d1lta.prison.enchants.book.SacredBook;
import me.d1lta.prison.enchants.book.VampirismBook;
import me.d1lta.prison.enchants.book.BlindnessBook;
import me.d1lta.prison.enchants.book.BoxerBook;
import me.d1lta.prison.enchants.book.ConfusionBook;
import me.d1lta.prison.enchants.book.FuryBook;
import me.d1lta.prison.enchants.book.NinjaBook;
import me.d1lta.prison.enchants.book.StrenghteningBook;
import me.d1lta.prison.enchants.book.ToxicBook;
import me.d1lta.prison.enchants.book.VortexBook;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.RomanNumeration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public enum Enchantments {

    NULL("NULL", "NULL", null),
    HAMMER("hammer", "Молот", TextColor.color(255, 172, 0)),
    VAMPIRISM("vampirism", "Вампиризм", TextColor.color(255, 0, 20)),
    SACRED("sacred", "Просвещённый", TextColor.color(255, 0, 77)),
    FIERY("fiery", "Раскалённый", TextColor.color(255, 255, 0)),
    VORTEX("vortex", "Вихрь", TextColor.color(255, 172, 0)),
    TOXIC("toxic", "Токсичный", TextColor.color(255, 255, 0)),
    BOXER("boxer", "Боксёр", TextColor.color(255, 255, 0)),
    BLINDNESS("blindness", "Слепота", TextColor.color(130, 133, 134)),
    CONFUSION("confusion", "Замешательство", TextColor.color(130, 133, 134)),
    FURY("fury", "Ярость", TextColor.color(255, 0, 20)),
    NINJA("ninja", "Ниндзя", TextColor.color(255, 255, 0)),
    STRENGHTENING("strenghtening", "Укрепление", TextColor.color(130, 133, 134)),
    CATGRACE("catgrace", "Кошачья грация", TextColor.color(255, 255, 134)),
    ARCHAEOLOGY("archaeology", "Археолог", TextColor.color(255, 172, 0));

    private final String name;
    private final String localisation;
    private final TextColor textColor;

    Enchantments(String name, String localisation, TextColor textColor) {
        this.name = name;
        this.localisation = localisation;
        this.textColor = textColor;
    }

    public List<Material> getApplicableTools() {
        return this.getBook(1,0).applicableTo;
    }

    public String getName() {
        return name;
    }

    public String getLocalisation() {
        return localisation;
    }

    public TextColor getTextColor() {
        return textColor;
    }

    public Component getColoredName() {
        return ComponentUtils.component(this.localisation, this.textColor);
    }

    public EnchantmentBook getBook(int lvl, int chance) {
        return getBook(name, lvl, chance);
    }

    public Component getColoredNameWithLevel(int lvl) {
        return ComponentUtils.component(this.localisation, this.textColor).append(ComponentUtils.component(" " + RomanNumeration.get(lvl), this.textColor));
    }

    public static EnchantmentBook getBook(String enchantment, int lvl, int chance) {
        switch (enchantment) {
            case "hammer" -> { return new HammerBook(lvl, chance); }
            case "vampirism" -> { return new VampirismBook(lvl, chance); }
            case "archaeology" -> { return new ArchaeologyBook(lvl, chance); }
            case "sacred" -> { return new SacredBook(lvl, chance); }
            case "fiery" -> { return new FieryBook(lvl, chance); }
            case "blindness" -> { return new BlindnessBook(lvl,chance); }
            case "vortex" -> { return new VortexBook(lvl,chance); }
            case "toxic" -> { return new ToxicBook(lvl,chance); }
            case "boxer" -> { return new BoxerBook(lvl,chance); }
            case "confusion" -> { return new ConfusionBook(lvl,chance); }
            case "fury" -> { return new FuryBook(lvl,chance); }
            case "ninja" -> { return new NinjaBook(lvl,chance); }
            case "strenghtening" -> { return new StrenghteningBook(lvl,chance); }
            case "catgrace" -> { return new CatGraceBook(lvl,chance); }
            default -> { return null; }
        }
    }

    public static Enchantments getEnchantment(String name) {
        for (Enchantments e: Enchantments.values()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return NULL;
    }

}
