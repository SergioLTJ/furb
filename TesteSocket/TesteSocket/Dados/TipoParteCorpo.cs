using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public enum TipoParteCorpo
    {
        [EnumMember(Value = "0")]
        CEREBRO = 0,

        [EnumMember(Value = "1")]
        CORACAO = 1,

        [EnumMember(Value = "2")]
        PULMAO = 2,

        [EnumMember(Value = "3")]
        INTESTINO = 3,
    }
}
