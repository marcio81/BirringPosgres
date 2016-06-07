(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CervesaDetailController', CervesaDetailController);

    CervesaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Cervesa', 'Precio', 'Evaluar', 'Comentario', 'Ubicacion'];

    function CervesaDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Cervesa, Precio, Evaluar, Comentario, Ubicacion) {
        var vm = this;

        entity.$promise.then(function(data) {
            vm.cervesa = data;
           Cervesa.verComentarios({id:vm.cervesa.id},function (response) {

                vm.cervezaComentarios = response;

            });

        });


        vm.load = function (id) {
            Cervesa.get({id: id}, function(result) {
                vm.cervesa = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterApp:cervesaUpdate', function(event, result) {
            vm.cervesa = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

    }
})();
