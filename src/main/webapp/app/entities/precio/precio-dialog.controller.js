(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('PrecioDialogController', PrecioDialogController);

    PrecioDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Precio', 'Ubicacion', 'Cervesa', 'User', 'ubicacion'];

    function PrecioDialogController ($scope, $stateParams, $uibModalInstance, entity, Precio, Ubicacion, Cervesa, User, ubicacion) {
        var vm = this;

        ubicacion.$promise.then(function (data) {
          vm.ubicacion=data;
        })

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
                vm.precio.cervesa = {id: vm.ubicacion.cervesas[0].id}
                vm.precio.ubicacion = {id: vm.ubicacion.id};
                Precio.save(vm.precio, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
