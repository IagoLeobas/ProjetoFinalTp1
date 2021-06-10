-- Table: public.usuario

-- DROP TABLE public.usuario;

CREATE TABLE public.usuario
(
    idusu integer NOT NULL DEFAULT nextval('usuario_idusu_seq'::regclass),
    nome character varying(80) COLLATE pg_catalog."default" NOT NULL,
    cpf character varying(20) COLLATE pg_catalog."default" NOT NULL,
    email character varying(80) COLLATE pg_catalog."default" NOT NULL,
    login character varying(80) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(80) COLLATE pg_catalog."default" NOT NULL,
    tipo integer,
    CONSTRAINT usuario_pkey PRIMARY KEY (idusu)
)

TABLESPACE pg_default;

ALTER TABLE public.usuario
    OWNER to postgres;