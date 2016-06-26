(function (angular) {

    angular.module('relatorios', [])
        .controller('RelatorioController', ['$http', '$scope', 
            function ($http, $scope) {
        		$scope.dadosTermosPesquisa;
        		$scope.dadosUsuarios;
        		$scope.dadosFavoritas;
        	
                google.charts.load('current', { 'packages': ['corechart', 'table'] });

                google.charts.setOnLoadCallback(obterDadosGraficoTermosPesquisa);
                google.charts.setOnLoadCallback(obterDadosGraficoMusicasFavoritas);
                google.charts.setOnLoadCallback(obterDadosGraficoUsuariosMaisAtivos);

                window.onresize = function() {
                	desenharGraficoMusicas();
                	desenharGraficoTermosPesquisa();
                	desenharGraficoUsuarios();
                }
                
                function obterDadosGraficoTermosPesquisa() {
                    realizarRequisicaoDados('termos', function(retorno) {
                    	$scope.dadosTermosPesquisa = retorno.data;
                    	desenharGraficoTermosPesquisa();
                    });
                }

                function obterDadosGraficoMusicasFavoritas() {
                    realizarRequisicaoDados('favoritas', function(retorno) {
                    	$scope.dadosFavoritas = retorno.data;
                    	desenharGraficoMusicas();
                    });
                }

                function obterDadosGraficoUsuariosMaisAtivos() {
                    realizarRequisicaoDados('usuarios', function(retorno) {
                    	$scope.dadosUsuarios = retorno.data;
                    	desenharGraficoUsuarios();
                    });
                }
                
                function desenharGraficoTermosPesquisa() {
                	var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Termo');
                    data.addColumn('number', 'Quantidade');
                	
                	for (var i = 0; i < $scope.dadosTermosPesquisa.length; i++) {
                		data.addRow($scope.dadosTermosPesquisa[i].dados);
                	}
                	
                    var chart = new google.visualization.PieChart(document.getElementById('divTermosMaisPesquisados'));
                    chart.draw(data, { chartArea:{ width:'90%', height:'80%' } });
                }
                
                function desenharGraficoMusicas() {
                	var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Música');
                    data.addColumn('number', 'Favorita');
                	
                	for (var i = 0; i < $scope.dadosFavoritas.length; i++) {
                		data.addRow($scope.dadosFavoritas[i].dados);
                	}
                	
                    var chart = new google.visualization.ColumnChart(document.getElementById('divMusicasMaisFavoritas'));
                    chart.draw(data, { vAxis: { format: '#' } });
                }

                function desenharGraficoUsuarios() {
                	var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Usuário');
                    data.addColumn('number', 'Edições');
                	
                	for (var i = 0; i < $scope.dadosUsuarios.length; i++) {
                		data.addRow($scope.dadosUsuarios[i].dados);
                	}

                    var table = new google.visualization.Table(document.getElementById('divUsuariosMaisAtivos'));
                    table.draw(data, { 'width': '100%', showRowNumber: true });
                }
                
                function realizarRequisicaoDados(tipoDado, callback) {
                	$http({
                    	method: 'POST',
                        url: '../../Musicas/RelatoriosServlet',
                        contentType: 'application/json',
                        data: { tipo: tipoDado },
                    }).then(function (retorno) { callback(retorno) });
                }
            }]);
})(window.angular);