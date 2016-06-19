(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('PrecioDialogController2', PrecioDialogController2);

    PrecioDialogController2.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Precio', 'Ubicacion', 'Cervesa', 'User'];

    function PrecioDialogController2 ($scope, $stateParams, $uibModalInstance, entity, Precio, Ubicacion, Cervesa, User) {
        var vm = this;
        

        vm.precio = entity;
        vm.ubicacions = Ubicacion.query();
        vm.cervesas = Cervesa.query();
        vm.users = User.query();
        vm.load = function(id) {
            Precio.get({id : id}, function(result) {
                vm.precio = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterApp:precioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.precio.id !== null) {
                Precio.update(vm.precio, onSaveSuccess, onSaveError);
            } else {
                Precio.save(vm.precio, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
