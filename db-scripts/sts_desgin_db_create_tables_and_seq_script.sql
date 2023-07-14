CREATE TABLE public."permission_groups"
(
    id integer NOT NULL,
    group_name character varying NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public."permission_groups" OWNER to postgres;


CREATE TABLE public."permissions"
(
    id integer NOT NULL,
    user_email character varying(100),
    permission_level character varying(4),
    group_id integer,
    PRIMARY KEY (id),
    CONSTRAINT permission_email_unq_idx UNIQUE (user_email),
    CONSTRAINT permission_group_id_fk FOREIGN KEY (group_id)
        REFERENCES public."permission_groups" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
);

ALTER TABLE IF EXISTS public."permissions" OWNER to postgres;




CREATE TABLE public."item"
(
    id integer NOT NULL,
    type character varying(10) NOT NULL,
    name character varying(2000) NOT NULL,
    permission_group_id integer,
    PRIMARY KEY (id),
	CONSTRAINT item_name_unq_idx UNIQUE (name),
    CONSTRAINT item_perm_grp_id_fk FOREIGN KEY (permission_group_id)
        REFERENCES public."permission_groups" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
);

ALTER TABLE IF EXISTS public."item" OWNER to postgres;



CREATE TABLE public."files"
(
    id integer NOT NULL,
    "binary" bytea NULL,
    item_id integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT files_itm_id_fk FOREIGN KEY (item_id)
        REFERENCES public."item" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
);

ALTER TABLE IF EXISTS public."files" OWNER to postgres;


CREATE SEQUENCE public."permission_group_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9999999999999999;

ALTER SEQUENCE public."permission_group_seq" OWNER TO postgres;


CREATE SEQUENCE public."permission_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9999999999999999;

ALTER SEQUENCE public."permission_seq" OWNER TO postgres;


CREATE SEQUENCE public."item_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9999999999999999;

ALTER SEQUENCE public."item_seq" OWNER TO postgres;


CREATE SEQUENCE public."file_seq"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9999999999999999;

ALTER SEQUENCE public."file_seq" OWNER TO postgres;


INSERT INTO public.permission_groups(id, group_name)  VALUES
(nextval('public."permission_group_seq"'), 'Admin'),
(nextval('public."permission_group_seq"'), 'Manager'),
(nextval('public."permission_group_seq"'), 'Clerk');

INSERT INTO public.permissions( id, user_email, permission_level, group_id) VALUES
(nextval('public."permission_seq"'), 'mohammad.ahmad@gmail.com', 'EDIT', 1),
(nextval('public."permission_seq"'), 'fatma.farid@hotmail.com', 'EDIT', 1),
(nextval('public."permission_seq"'), 'malika.farid@hotmail.com', 'EDIT', 2),
(nextval('public."permission_seq"'), 'hamada.elgamal@hotmail.com', 'VIEW', 2),
(nextval('public."permission_seq"'), 'ali.husein@yahoo.com', 'VIEW', 3),
(nextval('public."permission_seq"'), 'said.gamal@hotmail.com', 'VIEW', 3),
(nextval('public."permission_seq"'), 'george.wasfy@gmail.com', 'VIEW', 3),
(nextval('public."permission_seq"'), 'nada.hamdy@gmail.com', 'VIEW', 3),
(nextval('public."permission_seq"'), 'hoda.ayman@hotmail.com', 'EDIT', 3);