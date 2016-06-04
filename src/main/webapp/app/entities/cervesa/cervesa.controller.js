(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CervesaController', CervesaController);

    CervesaController.$inject = ['$scope', '$state', 'DataUtils', 'Cervesa', 'ParseLinks', 'AlertService'];

    function CervesaController ($scope, $state, DataUtils, Cervesa, ParseLinks, AlertService) {
        var vm = this;
        vm.cervesas = [];
        //probar instanciando aqui
        //vm.topCervesas = [];
        vm.predicate = 'id';
        vm.reverse = true;
        vm.page = 0;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.loadAll = function() {
            Cervesa.query({
                page: vm.page,
                size: 20,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.cervesas.push(data[i]);
                }
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        };

        vm.getTopCervezas = function () {
            Cervesa.consultaTop(function (response) {
                //$http.get("api/cervesas").then(function (response) {
                vm.topCervesas = response;
                //});
            });
        };
        vm.getBuCervesas = function () {
            Cervesa.consultaBu({consultaBu: vm.buCervesas}, function (response) {

                vm.cervesas = response;

            });
        };
       

        vm.filtrarPor = function (filtro) {
            vm.filtracion = filtro;
        };

        vm.reset = function() {
            vm.page = 0;
            vm.cervesas = [];
            vm.loadAll();
        };
        vm.loadPage = function(page) {
            vm.page = page;
            vm.loadAll();
        };

        vm.loadAll();

        vm.getTopCervezas();
        vm.getBuCervesas();

/**/
    }
})();
