(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('PrecioDeleteController',PrecioDeleteController);

    PrecioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Precio'];

    function PrecioDeleteController($uibModalInstance, entity, Precio) {
        var vm = this;
        vm.precio = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Precio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
