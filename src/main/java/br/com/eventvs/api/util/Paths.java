package br.com.eventvs.api.util;

public class Paths {

    public static final String PATH_ADMINISTRADOR = "/administradores";
    public static final String PATH_ENDERECO = "/enderecos";
    public static final String PATH_INSCRICAO = "/inscricoes";
    public static final String PATH_PARTICIPANTE = "/participantes";
    public static final String PATH_PESSOA = "/pessoas";
    public static final String PATH_PRODUTOR = "/produtores";

    /* Categorias */
    public static final String PATH_CATEGORIA = "/categorias";
    public static final String PATH_BUSCAR_CATEGORIA_POR_NOME = "/nome";
    public static final String PATH_BUSCAR_CATEGORIA_POR_DESCRICAO = "/descricao";

    /* Inscrições */
    public static final String PATH_BUSCAR_INSCRICAO_ID = "/{inscricaoId}";
    public static final String PATH_VIZUALIZAR_PARTICIPANTES = "/eventos/{eventoId}";
    public static final String PATH_INSCRICAO_ID_CANCELAR = "/{inscricaoId}/cancelar";

    /* Criar contas */
    public static final String PATH_CRIAR_CONTA = "/criar";
    public static final String PATH_CRIAR_CONTA_PARTICIPANTE = "/participantes";
    public static final String PATH_CRIAR_CONTA_PRODUTOR = "/produtores";

    /* Eventos */
    public static final String PATH_EVENTO = "/eventos";
    public static final String PATH_EVENTO_ID = "/{eventoId}";
    public static final String PATH_EVENTO_ID_CANCELAR = "/{eventoId}/cancelar";
    public static final String PATH_EVENTO_ID_PUBLICAR = "/{eventoId}/publicar";
    public static final String PATH_EVENTOS_PUBLICADOS = "/publicados";
    public static final String PATH_EVENTOS_PUBLICADOS_POR_CATEGORIA = PATH_EVENTOS_PUBLICADOS + "/categoria/{categoriaId}";
    public static final String PATH_EVENTOS_NAO_PUBLICADOS = "/nao-publicados";
    public static final String PATH_EVENTOS_NAO_PUBLICADOS_POR_CATEGORIA = PATH_EVENTOS_NAO_PUBLICADOS + "/categoria/{categoriaId}";
    public static final String PATH_EVENTOS_NAO_PUBLICADOS_POR_NOME = PATH_EVENTOS_NAO_PUBLICADOS + "/nome";
    public static final String PATH_EVENTOS_NAO_PUBLICADOS_ENTRE_DATAS = PATH_EVENTOS_NAO_PUBLICADOS + "/entre-datas";
    public static final String PATH_EVENTOS_PUBLICADOS_POR_NOME = PATH_EVENTOS_PUBLICADOS + "/nome";
    public static final String PATH_EVENTOS_PUBLICADOS_ENTRE_DATAS = PATH_EVENTOS_PUBLICADOS + "/entre-datas";

}