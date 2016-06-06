(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CervesaDetailController', CervesaDetailController);

    CervesaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Cervesa', 'Precio', 'Evaluar', 'Comentario', 'Ubicacion'];

    function CervesaDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Cervesa, Precio, Evaluar, Comentario, Ubicacion) {
        var vm = this;
        vm.cervesa = entity;
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

//esta comentado abajo
        vm.getComentariosCervezaID = function () {
            Cervesa.verComentarios({id:vm.idCerveza})(function (response) {
                //$http.get("api/cervesas").then(function (response) {
                vm.cervezaComentarios = response;
                //});
            });
        };

        <!--COMENTARIO-->
        /*vm.getComentCervesas = function () {
            Comentario.comenta(function (response) {

                vm.comentarios = response;

            });
        };*/

         //vm.getComentariosCervezaID();
    }
})();
