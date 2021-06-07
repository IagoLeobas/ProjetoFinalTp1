-- Table: public.itemvenda

-- DROP TABLE public.itemvenda;

CREATE TABLE public.itemvenda
(
    iditem integer NOT NULL,
    idvenda integer NOT NULL,
    quantidade integer NOT NULL,
    valorunit numeric(10,2),
    CONSTRAINT iditem FOREIGN KEY (iditem)
        REFERENCES public.produto (idprod) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT idvenda FOREIGN KEY (idvenda)
        REFERENCES public.venda (idvenda) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.itemvenda
    OWNER to postgres;