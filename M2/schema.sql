DROP TABLE IF EXISTS Battle_log;
DROP TABLE IF EXISTS Planet_battle_defense;
DROP TABLE IF EXISTS Planet_battle_army;
DROP TABLE IF EXISTS Enemy_army;
DROP TABLE IF EXISTS Battle_stats;
DROP TABLE IF EXISTS Planet_stats;

CREATE TABLE Planet_stats (
    planet_id INT NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    resource_metal_amount INT DEFAULT 0,
    resource_deuterium_amount INT DEFAULT 0,
    technology_defense_level INT DEFAULT 1,
    technology_attack_level INT DEFAULT 1,
    battles_counter INT DEFAULT 0,
    missile_launcher_remaining INT DEFAULT 0,
    ion_cannon_remaining INT DEFAULT 0,
    plasma_cannon_remaining INT DEFAULT 0,
    light_hunter_remaining INT DEFAULT 0,
    heavy_hunter_remaining INT DEFAULT 0,
    battleship_remaining INT DEFAULT 0,
    armored_ship_remaining INT DEFAULT 0
);

CREATE TABLE Battle_stats (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    resource_metal_acquired INT DEFAULT 0,
    resource_deuterium_acquired INT DEFAULT 0,
    PRIMARY KEY (planet_id, num_battle),
    FOREIGN KEY (planet_id) REFERENCES Planet_stats(planet_id)
);

CREATE TABLE Planet_battle_army (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    light_hunter_built INT DEFAULT 0,
    light_hunter_destroyed INT DEFAULT 0,
    heavy_hunter_built INT DEFAULT 0,
    heavy_hunter_destroyed INT DEFAULT 0,
    battleship_built INT DEFAULT 0,
    battleship_destroyed INT DEFAULT 0,
    armored_ship_built INT DEFAULT 0,
    armored_ship_destroyed INT DEFAULT 0,
    PRIMARY KEY (planet_id, num_battle),
    FOREIGN KEY (planet_id, num_battle) REFERENCES Battle_stats(planet_id, num_battle)
);

CREATE TABLE Planet_battle_defense (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    missile_launcher_built INT DEFAULT 0,
    missile_launcher_destroyed INT DEFAULT 0,
    ion_cannon_built INT DEFAULT 0,
    ion_cannon_destroyed INT DEFAULT 0,
    plasma_cannon_built INT DEFAULT 0,
    plasma_cannon_destroyed INT DEFAULT 0,
    PRIMARY KEY (planet_id, num_battle),
    FOREIGN KEY (planet_id, num_battle) REFERENCES Battle_stats(planet_id, num_battle)
);

CREATE TABLE Enemy_army (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    light_hunter_threat INT DEFAULT 0,
    light_hunter_destroyed INT DEFAULT 0,
    heavy_hunter_threat INT DEFAULT 0,
    heavy_hunter_destroyed INT DEFAULT 0,
    battleship_threat INT DEFAULT 0,
    battleship_destroyed INT DEFAULT 0,
    armored_ship_threat INT DEFAULT 0,
    armored_ship_destroyed INT DEFAULT 0,
    PRIMARY KEY (planet_id, num_battle),
    FOREIGN KEY (planet_id, num_battle) REFERENCES Battle_stats(planet_id, num_battle)
);

CREATE TABLE Battle_log (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    num_line INT NOT NULL,
    log_entry TEXT,
    PRIMARY KEY (planet_id, num_battle, num_line),
    FOREIGN KEY (planet_id, num_battle) REFERENCES Battle_stats(planet_id, num_battle)
);
