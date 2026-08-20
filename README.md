# MobVariants
Adds stronger variants for hostile mobs


### [CurseForge](https://www.curseforge.com/minecraft/mc-mods/mob-variants)

### [Modrinth](https://modrinth.com/mod/fryc-mob-variants)

-------------------------------------------------------------

## Datapacks:

Example datapack can be found [here](https://github.com/Xires87/FrycDatapacks/tree/1.21/frycmobvariants_example). Default conversion rules can be found [here](https://github.com/Xires87/MobVariants/tree/master/src/main/resources/data/frycmobvariants/mob_conversion_rules).

### Creating mob conversion rule

In `data/frycmobvariants/mob_conversion_rules/` create json file containing (name of file doesn't matter unless you want to replace one of the existing files):

```json
{
  "priority": 1,
  "target_mob": "MODID:SOME_MOB",
  "outcome_mob": "MODID:SOME_MOB",
  "equipment": {
    "keep_equipment": false,
    "init_equipment": false,
    "custom_equipment": [
      {
        "slot": "EQUIPMENT_SLOT",
        "item": "MODID:SOME_ITEM",
        "chance": 0.0
      },
      {
        "slot": "EQUIPMENT_SLOT",
        "item": "MODID:SOME_ITEM",
        "chance": 1.0
      }
    ]
  },
  "requirements": { 
    "type": "FUNCTION_TYPE"
  }
}
```

Common fields:
- `priority` (data type: `int`) - only one rule at a time can be applied: if requirements of many rules are met, the one with the highest priority is chosen (or randomly, if multiple rules have the same priority)
- `target_mob` (data type: `String`) - a mob that will be affected by this rule after spawning
- `outcome_mob` (data type: `String`) - a mob that will replace the mob from `target_mob` when requirements are met
- `equipment` (data type: `Object`):
    * `keep_equipment` (data type: `boolean`) - whether `outcome_mob` should keep `target_mob`'s equipment or not
    * `init_equipment` (data type: `boolean`) - whether `outcome_mob` should initialize its equipment (may replace already worn items if there are any)
    * `custom_equipment` (data type: `List<Object>`) - list of items `outcome_mob` may get (may replace already worn items if there are any (called after equipment initialization)):
        * `slot` (data type: `String`) - slot in which the `item` will be equipped. Available values: "mainhand", "offhand", "feet", "legs", "chest", "head", "body"
        * `item` (data type: `String`) - item that will be equipped
        * `chance` (data type: `double`) - chance of equipping the `item`. 1.0 = 100%, 0.0 = 0%. The chance works a bit different than the one in config - each item is rolled independently and then the items are chosen randomly from the pool (depending on slots they are for). This means, if you have 2 items for mainhand with 100% chance, the item that will be equipped is chosen randomly
- `requirements` (data type: `Object`):
    * `type` (data type: `String`) - the type of function that will test if `target_mob` should be converted to `outcome_mob` (in other words - whether this rule should be applied or not). More info about functions below

Function-dependent fields:
- `value` (data type: `Number`/`String`) - it can be either number or name of config variable (of numeric type). For example, it can be `12` or `"zombifiedPiglinConvertChance"` (only numeric non-nested variables are supported - for example, `"undeadWarriorsMinTippedArrowsCount"` won't work because it's under `"UndeadWarriorAttributes"`)
- (Optional) `multiplier` (data type: `double`) - can be used to tweak `value`. Defaults to 1.0. Works only for `value` fields
- `comparator` (data type: `String`) - determines how two values will be compared. Available values: "LOWER_THAN", "HIGHER_THAN", "EQUAL"

### Functions (for requirements)

Available functions:
- `AND` - holds a list of functions: all of them must pass for this function to pass:
```json
{
  "requirements": {
    "type": "AND",
    "functions": [
      {
        "type": "FUNCTION_TYPE"
      },
      {
        "type": "FUNCTION_TYPE"
      }
    ]
  }
}
```
- `OR` - holds a list of functions: at least one of them must pass for this function to pass:
```json
{
  "requirements": {
    "type": "OR",
    "functions": [
      {
        "type": "FUNCTION_TYPE"
      },
      {
        "type": "FUNCTION_TYPE"
      }
    ]
  }
}
```
- `NOT` - holds a function that must not pass for this function to pass:
```json
{
  "requirements": {
    "type": "NOT",
    "function": {
      "type": "FUNCTION_TYPE"
    }
  }
}
```
- `fixed_chance` - a fixed chance for this function to pass (0.0 - 1.0)
```json
{
  "requirements": {
    "type": "fixed_chance",
    "value": 0.5
  }
}
```
- `mob_position_chance` - a chance that depends on mob's position. `base_value` is the base chance. `x`, `y` and `z` modify base value (for example, when `y` is set to -0.01 and mob is at 52 Y level, chance will be lowered by 0.52 (52%)). `x`, `y` and `z` are all optional (they default to 0.0)
```json
{
  "requirements": {
    "type": "mob_position_chance",
    "base_value": {
      "value": 0.5
    },
    "y": {
      "value": -0.01
    },
    "x": {
      "value": 0.00001
    },
    "z": {
      "value": -0.00001
    }
  }
}
```
- `mob_position` - compares coordinates with specified values. `x`, `y` and `z` are all optional (omit the ones you don't want to check)
```json
{
  "requirements": {
    "type": "mob_position",
    "y": {
      "comparator": "LOWER_THAN",
      "value": 26
    },
    "x": {
      "comparator": "HIGHER_THAN",
      "value": 500
    },
    "z": {
      "comparator": "EQUAL",
      "value": 0
    }
  }
}
```
- `biome` - checks if mob is in one of the biomes from tag
```json
{
  "requirements": {
    "type": "biome",
    "tag": "MODID:CHOSEN_BIOME_TAG"
  }
}
```
- `config_value_number` - compares config value with specified value
```json
{
  "requirements": {
    "type": "config_value_number",
    "variable_name": "FRYCMOBVARIANTS_CONFIG_VARIABLE_NAME",
    "comparator": "LOWER_THAN",
    "comparison_value": 0
  }
}
```

## Credits:
- Textures made by **sheslong**
- Translations made by:
    * **Xires87** (pl_pl)
    * **Rad586** (zh_cn)
