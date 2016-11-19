using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public enum TipoDado
    {
        [EnumMember(Value = "0")]
        MOVEU_JOGADOR = 0,

        [EnumMember(Value = "1")]
        SELECIONOU_ITEM_PERGUNTA =  1,

        [EnumMember(Value = "2")]
        CRIOU_TELA_PERGUNTA = 2,

        [EnumMember(Value = "3")]
        CLICOU_CONFIRMAR_TELA_PERGUNTA = 3,

        [EnumMember(Value = "4")]
        ABRIU_TELA_MONTAR_CORPO = 4,

        [EnumMember(Value = "5")]
        MOVEU_PARTE = 5,
    }
}
