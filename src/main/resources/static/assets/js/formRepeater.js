$(document).on('change','.type-rsc', function (){
    let pc = $(this).parents(".select-type").siblings( ".pc" );
    let imp = $(this).parents(".select-type").siblings( ".imp" );
    if ($(this).val() == 1){
        pc.removeClass("d-none");
        pc.addClass("d-block");
        imp.removeClass("d-block");
        imp.addClass("d-none");
    }
    if ($(this).val() == 2){
        pc.removeClass("d-block");
        pc.addClass("d-none");
        imp.removeClass("d-none");
        imp.addClass("d-block");
    }
    if ($(this).val() == 0){
        pc.addClass("d-none");
        imp.addClass("d-none");
    }

} );

$(document).on('change','#affectation-type', function (){
    let moi = $("#aff-moi");
    let dep = $("#aff-dep");
    if ($(this).val() == 0){
        moi.removeClass("d-block");
        moi.addClass("d-none");
        dep.removeClass("d-block");
        dep.addClass("d-none");
    }
    if ($(this).val() == 1){
        moi.removeClass("d-none");
        moi.addClass("d-bloc");
        dep.removeClass("d-block");
        dep.addClass("d-none");
    }
    if ($(this).val() == 2){
        moi.removeClass("d-block");
        moi.addClass("d-none");
        dep.removeClass("d-none");
        dep.addClass("d-block");
    }
});
