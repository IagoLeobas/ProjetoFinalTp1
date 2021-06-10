-- Table: public.venda

-- DROP TABLE public.venda;

CREATE TABLE public.venda
(
    idvenda integer NOT NULL DEFAULT nextval('venda_idvenda_seq'::regclass),
    data date NOT NULL,
    idusuario integer,
    total numeric(10,2),
    CONSTRAINT venda_pkey PRIMARY KEY (idvenda),
    CONSTRAINT idusuario FOREIGN KEY (idusuario)
        REFERENCES public.usuario (idusu) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.venda
    OWNER to postgres;