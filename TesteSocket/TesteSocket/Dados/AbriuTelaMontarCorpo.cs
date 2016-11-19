using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace TesteSocket.Dados
{
    [DataContract]
    public class AbriuTelaMontarCorpo
    {
        [DataMember]
        public PartesPorJogador[] partesPorJogador { get; set; }
    }
}
