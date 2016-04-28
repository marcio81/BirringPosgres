(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('UbicacionDeleteController',UbicacionDeleteController);

    UbicacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ubicacion'];

    function UbicacionDeleteController($uibModalInstance, entity, Ubicacion) {
        var vm = this;
        vm.ubicacion = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Ubicacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
