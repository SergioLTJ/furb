(function (angular) {
    angular.module("servico-usuario-logado", [])
        .service("ServicoUsuarioLogado", ['$http',
            function ($http) {
                this.obterUsuarioLogado = function (callback) {
                     $http({
                         method: 'POST',
                         url: '../../Musicas/ControladorUsuarioLogado',
                         contentType: 'application/json',
                         data: { operacao: 'obterLogado' },
                     }).then(function (retorno) {
                         callback(retorno);
                     });
                }
            }]);
})(window.angular);