DELETE FROM plane_accessories;
INSERT INTO plane_accessories (type, name, level, power) VALUES
 ('GUN', 'MG 42', 1, 50),
 ('GUN', 'Cañón 20mm Hispano', 2, 65),
 ('GUN', 'Cohete V2 Alemán', 3, 80),
 ('ARMOR', 'Blindaje Ligero', 1, 20),
 ('ARMOR', 'Blindaje Medio', 2, 35),
 ('ARMOR', 'Blindaje Pesado', 3, 50);

 INSERT INTO planes (name, model, health, base_health, attack, fuel, hangar_id, equipped_accessory) VALUES
   -- Para el usuario con id 7 (Almami)
   ('Spitfire', 'Famoso caza británico de la RAF', 120, 120, 75, 100, 13, NULL),
   ('Messerschmitt Bf 109', 'Caza alemán versátil y rápido', 110, 110, 80, 100, 13, 4),

   -- Para el usuario con id 8 (Irene)
   ('P-51 Mustang', 'Avión de escolta estadounidense con gran autonomía', 130, 130, 85, 100, 14, NULL),
   ('Mitsubishi A6M Zero', 'Ligero y ágil, utilizado por la Armada Imperial Japonesa', 100, 100, 90, 100, 14, 1),

   -- Para el usuario con id 9 (Pili)
   ('Focke-Wulf Fw 190', 'Caza alemán robusto y poderoso', 140, 140, 95, 100, 15, 2),
   ('Yakovlev Yak-3', 'Caza soviético ágil y veloz en combate', 115, 115, 78, 100, 15, NULL);

