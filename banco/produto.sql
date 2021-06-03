-- Table: public.produto

-- DROP TABLE public.produto;

CREATE TABLE public.produto
(
    idprod integer NOT NULL DEFAULT nextval('produto_idprod_seq'::regclass),
    produto character varying(80) COLLATE pg_catalog."default" NOT NULL,
    descricao character varying(80) COLLATE pg_catalog."default" NOT NULL,
    categoria character varying(80) COLLATE pg_catalog."default" NOT NULL,
    preco numeric(10,2) NOT NULL,
    marca character varying(80) COLLATE pg_catalog."default" NOT NULL,
    origem character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT produto_pkey PRIMARY KEY (idprod)
)

TABLESPACE pg_default;

ALTER TABLE public.produto
    OWNER to postgres;