(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CervesaDetailController', CervesaDetailController);

    CervesaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Cervesa', 'Precio', 'Evaluar', 'Comentario', 'Ubicacion','NgMap'];

    function CervesaDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Cervesa, Precio, Evaluar, Comentario, Ubicacion, NgMap) {
        var vm = this;

        NgMap.getMap().then(function(map) {
            vm.map = map;

           /* vm.showCustomMarker= function(evt, ubi) {

                map.customMarkers.foo.setVisible(true);
                map.customMarkers.foo.setPosition(this.getPosition());


                vm.uName=ubi.ubiName;
                vm.uDirec=ubi.direccion;
            };
            vm.closeCustomMarker= function(evt) {
                this.style.display = 'none';
                vm.test=1;
            };*/


        });
        vm.showDetail = function(e, shop) {
            vm.shop = shop;
            vm.map.showInfoWindow('yoyo', shop.id.toString());
        };

         vm.hideDetail = function() {
             vm.map.hideInfoWindow('yoyo');
         };
        vm.mouseover = function() {
            console.log('mouseover');
        };

        entity.$promise.then(function(data) {
            vm.cervesa = data;
           Cervesa.verComentarios({id:vm.cervesa.id},function (response) {

                vm.cervezaComentarios = response;

            });

        });

        entity.$promise.then(function(data) {
            vm.cervesa = data;
            Cervesa.verPrecioMedio({id:vm.cervesa.id},function (response) {

                vm.cervezaPrecioMedio = response;

            });

        });

        entity.$promise.then(function(data) {
            vm.cervesa = data;
            Cervesa.verUbicaciones({id:vm.cervesa.id},function (response) {

                vm.cervezaUbication = response;

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
