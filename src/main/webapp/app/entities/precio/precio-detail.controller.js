(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('PrecioDetailController', PrecioDetailController);

    PrecioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Precio', 'Ubicacion', 'Cervesa', 'User'];

    function PrecioDetailController($scope, $rootScope, $stateParams, entity, Precio, Ubicacion, Cervesa, User) {
        var vm = this;
        vm.precio = entity;
        vm.load = function (id) {
            Precio.get({id: id}, function(result) {
                vm.precio = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterApp:precioUpdate', function(event, result) {
            vm.precio = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
