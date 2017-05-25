(function() {
    'use strict';

    angular
        .module('pruebaScrollApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scroll', {
            parent: 'entity',
            url: '/scroll',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pruebaScrollApp.scroll.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scroll/scrolls.html',
                    controller: 'ScrollController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scroll');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('scroll-detail', {
            parent: 'scroll',
            url: '/scroll/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pruebaScrollApp.scroll.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scroll/scroll-detail.html',
                    controller: 'ScrollDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scroll');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Scroll', function($stateParams, Scroll) {
                    return Scroll.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'scroll',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('scroll-detail.edit', {
            parent: 'scroll-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scroll/scroll-dialog.html',
                    controller: 'ScrollDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scroll', function(Scroll) {
                            return Scroll.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scroll.new', {
            parent: 'scroll',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scroll/scroll-dialog.html',
                    controller: 'ScrollDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                prueba: null,
                                prueba2: null,
                                prueba3: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scroll', null, { reload: 'scroll' });
                }, function() {
                    $state.go('scroll');
                });
            }]
        })
        .state('scroll.edit', {
            parent: 'scroll',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scroll/scroll-dialog.html',
                    controller: 'ScrollDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scroll', function(Scroll) {
                            return Scroll.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scroll', null, { reload: 'scroll' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scroll.delete', {
            parent: 'scroll',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scroll/scroll-delete-dialog.html',
                    controller: 'ScrollDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Scroll', function(Scroll) {
                            return Scroll.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scroll', null, { reload: 'scroll' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
