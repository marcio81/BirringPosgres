(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('ComentarioDialogController', ComentarioDialogController);

    ComentarioDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comentario', 'Cervesa', 'User'];

    function ComentarioDialogController ($scope, $stateParams, $uibModalInstance, entity, Comentario, Cervesa, User) {
        var vm = this;
        vm.comentario = entity;
        vm.cervesas = Cervesa.query();
        vm.users = User.query();
        vm.load = function(id) {
            Comentario.get({id : id}, function(result) {
                vm.comentario = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterApp:comentarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.comentario.id !== null) {
                Comentario.update(vm.comentario, onSaveSuccess, onSaveError);
            } else {
                Comentario.save(vm.comentario, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
