'use strict';

angular.module('namaaApaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('grainType1', {
                parent: 'entity',
                url: '/grainType1s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.grainType1.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grainType1/grainType1s.html',
                        controller: 'GrainType1Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('grainType1');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('grainType1.detail', {
                parent: 'entity',
                url: '/grainType1/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.grainType1.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grainType1/grainType1-detail.html',
                        controller: 'GrainType1DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('grainType1');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GrainType1', function($stateParams, GrainType1) {
                        return GrainType1.get({id : $stateParams.id});
                    }]
                }
            })
            .state('grainType1.new', {
                parent: 'grainType1',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/grainType1/grainType1-dialog.html',
                        controller: 'GrainType1DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {spelt: null, corn: null, millet: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('grainType1', null, { reload: true });
                    }, function() {
                        $state.go('grainType1');
                    })
                }]
            })
            .state('grainType1.edit', {
                parent: 'grainType1',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/grainType1/grainType1-dialog.html',
                        controller: 'GrainType1DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GrainType1', function(GrainType1) {
                                return GrainType1.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('grainType1', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
