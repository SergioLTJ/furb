function vincularElementoNgApp(angular, elemento, app) {
    angular.element(document).ready(function () {
        angular.bootstrap(document.getElementById(elemento), [app]);
    });
}

function obterValorQueryString(nomeParametro) {
    var query = window.location.search.substring(1);
    var vars = query.split('&');
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split('=');
        if (decodeURIComponent(pair[0]) == nomeParametro) {
            return decodeURIComponent(pair[1]);
        }
    }
}