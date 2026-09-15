/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.lib;

import java.util.Locale;

import extrabiomes.utility.EnhancedConfiguration;

public enum ItemSettings {

    // @formatter:off
    LOGTURNER,
    SCARECROW,
    PASTE,
    DYE,
    SEED,
    CROP,
    FOOD;
    // @formatter:on

    private boolean enabled;

    ItemSettings() {
        enabled = true;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void load(EnhancedConfiguration configuration, boolean update) {
        enabled = configuration.get("item", this + ".enabled", this.enabled)
            .getBoolean();
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase(Locale.ENGLISH);
    }

}
