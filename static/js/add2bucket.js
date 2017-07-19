// JavaScript Document
/*(function($) {
$.extend({
add2cart: function(source_id, withSrc_cls, target_id, callback)
{
var source = $(source_id).parents("."+withSrc_cls);
var target = $('#' + target_id );
var shadow = $('.' + withSrc_cls + '_shadow');

if( !shadow.attr('id') ) {
$('body').prepend('<div id="'+withSrc_cls+'_shadow" style="display: none; background-color: #FFF; border:1px solid #C3C3C3; position: static; top: 0px; z-index: 100000;">&nbsp;</div>');
var shadow = $('#'+withSrc_cls+'_shadow');
}
if( !shadow ) {
alert('Cannot create the shadow div');
}
shadow.width(source.css('width')).height(source.css('height')).css('top', source.offset().top).css('left', source.offset().left).css('opacity', 0.5).show(); 
shadow.css('position', 'absolute');
shadow.animate( { width: target.innerWidth(), height: target.innerHeight(), top: target.offset().top, left: target.offset().left, backgroundColor: '#FFF' }, { duration:1000} )
shadow.animate( { width: target.innerWidth(), height: target.innerHeight(), top: target.offset().top, left: target.offset().left, backgroundColor: '#F3F3F3' }, { duration:400} )
.animate( { opacity:0 }, { duration:500, complete:function(){ $('#'+withSrc_cls+'_shadow').remove(); } });
}
});
})(jQuery);*/
