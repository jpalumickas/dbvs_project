-- ----------------------------
--  Sequence structure for "cars_id_seq"
-- ----------------------------
DROP SEQUENCE IF EXISTS "cars_id_seq";
CREATE SEQUENCE "cars_id_seq" INCREMENT 1 START 9 MAXVALUE 9223372036854775807 MINVALUE 1 CACHE 1;
ALTER TABLE "cars_id_seq" OWNER TO "justas";

-- ----------------------------
--  Sequence structure for "employees_id_seq"
-- ----------------------------
DROP SEQUENCE IF EXISTS "employees_id_seq";
CREATE SEQUENCE "employees_id_seq" INCREMENT 1 START 7 MAXVALUE 9223372036854775807 MINVALUE 1 CACHE 1;
ALTER TABLE "employees_id_seq" OWNER TO "justas";

-- ----------------------------
--  Sequence structure for "fuel_vouchers_id_seq"
-- ----------------------------
DROP SEQUENCE IF EXISTS "fuel_vouchers_id_seq";
CREATE SEQUENCE "fuel_vouchers_id_seq" INCREMENT 1 START 3 MAXVALUE 9223372036854775807 MINVALUE 1 CACHE 1;
ALTER TABLE "fuel_vouchers_id_seq" OWNER TO "justas";

-- ----------------------------
--  Sequence structure for "positions_id_seq"
-- ----------------------------
DROP SEQUENCE IF EXISTS "positions_id_seq";
CREATE SEQUENCE "positions_id_seq" INCREMENT 1 START 4 MAXVALUE 9223372036854775807 MINVALUE 1 CACHE 1;
ALTER TABLE "positions_id_seq" OWNER TO "justas";

-- ----------------------------
--  Sequence structure for "secondment_types_id_seq"
-- ----------------------------
DROP SEQUENCE IF EXISTS "secondment_types_id_seq";
CREATE SEQUENCE "secondment_types_id_seq" INCREMENT 1 START 4 MAXVALUE 9223372036854775807 MINVALUE 1 CACHE 1;
ALTER TABLE "secondment_types_id_seq" OWNER TO "justas";

-- ----------------------------
--  Sequence structure for "secondments_id_seq"
-- ----------------------------
DROP SEQUENCE IF EXISTS "secondments_id_seq";
CREATE SEQUENCE "secondments_id_seq" INCREMENT 1 START 3 MAXVALUE 9223372036854775807 MINVALUE 1 CACHE 1;
ALTER TABLE "secondments_id_seq" OWNER TO "justas";

-- ----------------------------
--  Table structure for "positions"
-- ----------------------------
DROP TABLE IF EXISTS "positions";
CREATE TABLE "positions" (
	"id" int4 NOT NULL DEFAULT nextval('positions_id_seq'::regclass),
	"position_name" varchar(100)
)
WITH (OIDS=FALSE);
ALTER TABLE "positions" OWNER TO "justas";

-- ----------------------------
--  Records of "positions"
-- ----------------------------
BEGIN;
INSERT INTO "positions" VALUES ('1', 'Administratorius');
INSERT INTO "positions" VALUES ('2', 'Direktorius');
INSERT INTO "positions" VALUES ('3', 'Direktoriaus pavaduotojas');
COMMIT;

-- ----------------------------
--  Table structure for "cars"
-- ----------------------------
DROP TABLE IF EXISTS "cars";
CREATE TABLE "cars" (
	"id" int4 NOT NULL DEFAULT nextval('cars_id_seq'::regclass),
	"make" varchar(50) NOT NULL,
	"model" varchar(50) NOT NULL,
	"fuel_type" varchar(50) NOT NULL,
	"fuel_consumption" float8 NOT NULL DEFAULT 0,
	"year" int4 NOT NULL DEFAULT 0
)
WITH (OIDS=FALSE);
ALTER TABLE "cars" OWNER TO "justas";

-- ----------------------------
--  Records of "cars"
-- ----------------------------
BEGIN;
INSERT INTO "cars" VALUES ('1', 'Audi', '80', 'Benzinas', '9.2', '1996');
INSERT INTO "cars" VALUES ('2', 'Renault', 'Laguna', 'Benzinas', '8.1', '2003');
INSERT INTO "cars" VALUES ('3', 'Mazda', '6', 'Dyzelinas', '7.4', '2005');
INSERT INTO "cars" VALUES ('4', 'Volkswagen', 'Passat', 'Benzinas', '6.6', '2009');
INSERT INTO "cars" VALUES ('9', 'Toyota', 'Corolla', 'Benzinas', '8.2', '2005');
COMMIT;

-- ----------------------------
--  Table structure for "fuel_vouchers"
-- ----------------------------
DROP TABLE IF EXISTS "fuel_vouchers";
CREATE TABLE "fuel_vouchers" (
	"id" int4 NOT NULL DEFAULT nextval('fuel_vouchers_id_seq'::regclass),
	"date" date NOT NULL,
	"employee_id" int4 NOT NULL,
	"price_per_liter" float8 NOT NULL,
	"liters" float8 NOT NULL,
	"place" varchar NOT NULL,
	"voucher_code" varchar NOT NULL
)
WITH (OIDS=FALSE);
ALTER TABLE "fuel_vouchers" OWNER TO "justas";

-- ----------------------------
--  Records of "fuel_vouchers"
-- ----------------------------
BEGIN;
INSERT INTO "fuel_vouchers" VALUES ('1', '2012-11-07', '7', '4.84', '21.39', 'Statoil, Kaunas', 'M/22349');
INSERT INTO "fuel_vouchers" VALUES ('2', '2012-07-12', '3', '4.72', '8.21', 'Neste Oil', 'M/2321');
INSERT INTO "fuel_vouchers" VALUES ('3', '2011-11-12', '7', '4.45', '18.21', 'Lukoil', 'M/3212');
COMMIT;

-- ----------------------------
--  Table structure for "secondment_employees"
-- ----------------------------
DROP TABLE IF EXISTS "secondment_employees";
CREATE TABLE "secondment_employees" (
	"secondment_id" int4,
	"employee_id" int4
)
WITH (OIDS=FALSE);
ALTER TABLE "secondment_employees" OWNER TO "justas";

-- ----------------------------
--  Records of "secondment_employees"
-- ----------------------------
BEGIN;
INSERT INTO "secondment_employees" VALUES ('1', '1');
INSERT INTO "secondment_employees" VALUES ('1', '2');
INSERT INTO "secondment_employees" VALUES ('2', '3');
COMMIT;

-- ----------------------------
--  Table structure for "employees"
-- ----------------------------
DROP TABLE IF EXISTS "employees";
CREATE TABLE "employees" (
	"id" int4 NOT NULL DEFAULT nextval('employees_id_seq'::regclass),
	"first_name" varchar(30) NOT NULL,
	"last_name" varchar(30) NOT NULL,
	"birthday" date NOT NULL,
	"sex" char(1) NOT NULL,
	"dno" int8 NOT NULL,
	"position_id" int4 NOT NULL
)
WITH (OIDS=FALSE);
ALTER TABLE "employees" OWNER TO "justas";

-- ----------------------------
--  Records of "employees"
-- ----------------------------
BEGIN;
INSERT INTO "employees" VALUES ('2', 'Ona', 'Onutiene', '1987-02-07', 'M', '0', '1');
INSERT INTO "employees" VALUES ('3', 'Petras', 'Petraitis', '1989-12-23', 'V', '0', '2');
INSERT INTO "employees" VALUES ('1', 'Vardenis', 'Pavardenis', '1990-12-23', 'V', '39112091212', '3');
INSERT INTO "employees" VALUES ('7', 'Justas', 'Palumickas', '1992-08-26', 'V', '39208260000', '1');
COMMIT;

-- ----------------------------
--  Table structure for "car_employees"
-- ----------------------------
DROP TABLE IF EXISTS "car_employees";
CREATE TABLE "car_employees" (
	"car_id" int4,
	"employee_id" int4
)
WITH (OIDS=FALSE);
ALTER TABLE "car_employees" OWNER TO "justas";

-- ----------------------------
--  Records of "car_employees"
-- ----------------------------
BEGIN;
INSERT INTO "car_employees" VALUES ('3', '2');
INSERT INTO "car_employees" VALUES ('4', '1');
INSERT INTO "car_employees" VALUES ('9', '7');
COMMIT;

-- ----------------------------
--  Table structure for "secondment_types"
-- ----------------------------
DROP TABLE IF EXISTS "secondment_types";
CREATE TABLE "secondment_types" (
	"id" int4 NOT NULL DEFAULT nextval('secondment_types_id_seq'::regclass),
	"name" varchar(50) NOT NULL
)
WITH (OIDS=FALSE);
ALTER TABLE "secondment_types" OWNER TO "justas";

-- ----------------------------
--  Records of "secondment_types"
-- ----------------------------
BEGIN;
INSERT INTO "secondment_types" VALUES ('1', 'Apmokama');
INSERT INTO "secondment_types" VALUES ('3', 'Neapmokama');
INSERT INTO "secondment_types" VALUES ('2', 'Vietine komandiruote');
COMMIT;

-- ----------------------------
--  Table structure for "secondments"
-- ----------------------------
DROP TABLE IF EXISTS "secondments";
CREATE TABLE "secondments" (
	"id" int4 NOT NULL DEFAULT nextval('secondments_id_seq'::regclass),
	"from_where" varchar(100),
	"to_where" varchar(100),
	"type_id" int4 NOT NULL,
	"car_id" int4 NOT NULL,
	"money_for_secondment" varchar(20),
	"driven_km" float8,
	"from_date" date,
	"to_date" date
)
WITH (OIDS=FALSE);
ALTER TABLE "secondments" OWNER TO "justas";

-- ----------------------------
--  Records of "secondments"
-- ----------------------------
BEGIN;
INSERT INTO "secondments" VALUES ('1', 'Vilnius', 'Kaunas', '2', '1', '1200 Lt', '729.32', '2012-11-02', '2012-11-09');
INSERT INTO "secondments" VALUES ('2', 'Vilnius', 'Riga', '1', '4', '4500 Lt', '92', '2012-10-28', '2012-10-31');
COMMIT;

-- ----------------------------
--  Primary key structure for table "positions"
-- ----------------------------
ALTER TABLE "positions" ADD CONSTRAINT "positions_pkey" PRIMARY KEY ("id");

-- ----------------------------
--  Primary key structure for table "cars"
-- ----------------------------
ALTER TABLE "cars" ADD CONSTRAINT "cars_pkey" PRIMARY KEY ("id");

-- ----------------------------
--  Primary key structure for table "fuel_vouchers"
-- ----------------------------
ALTER TABLE "fuel_vouchers" ADD CONSTRAINT "fuel_vouchers_pkey" PRIMARY KEY ("id");

-- ----------------------------
--  Primary key structure for table "employees"
-- ----------------------------
ALTER TABLE "employees" ADD CONSTRAINT "employees_pkey" PRIMARY KEY ("id");

-- ----------------------------
--  Primary key structure for table "secondment_types"
-- ----------------------------
ALTER TABLE "secondment_types" ADD CONSTRAINT "secondment_types_pkey" PRIMARY KEY ("id");

-- ----------------------------
--  Primary key structure for table "secondments"
-- ----------------------------
ALTER TABLE "secondments" ADD CONSTRAINT "secondments_pkey" PRIMARY KEY ("id");

