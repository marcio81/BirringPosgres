(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('UbicacionDetailController', UbicacionDetailController);

    UbicacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Ubicacion', 'Precio', 'Cervesa'];

    function UbicacionDetailController($scope, $rootScope, $stateParams, entity, Ubicacion, Precio, Cervesa) {
        var vm = this;
        vm.ubicacion = entity;
        vm.load = function (id) {
            Ubicacion.get({id: id}, function(result) {
                vm.ubicacion = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterApp:ubicacionUpdate', function(event, result) {
            vm.ubicacion = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
